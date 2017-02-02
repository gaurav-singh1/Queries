package com.htmsl.rocq;

//import com.htmsl.rocq.configuration.HbaseConfigurationConstants;

import org.apache.hadoop.hbase.client.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
//import java.util.Collections;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
//import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.shubham.Another;

import com.google.common.base.Joiner;
import com.htmsl.batchprocessing.*;

import org.shubham.*;

public class MakeQueries {

	public static String DELIMITER_QUERY = "_";

	public static void main(String[] args) {

		// *******update the multimap : completely unfold the country,state,
		// region to finally make values corresponding to location key ...

		// India->Haryana->Gurugram,,, like this,,,
		// Location;{India->Haryana->Gurugram,.....,.....}

		// Similarly do for Model, Manufacturer, and Os....

		Map<String, List<String>> multiMap = fillTestData();

		System.out.println("Multimap is=" + multiMap.toString());

		Map<String, String> singleValueMap = new HashMap<String, String>();

		Map<String, String> singleValueMap_Frequent = new HashMap<String, String>();

		Map<String, String> singleValueMap_Infrequent = new HashMap<String, String>();

		Map<String, List<String>> multiValueMap = new HashMap<String, List<String>>();

		Map<String, List<String>> multiValueMap_Frequent = new HashMap<String, List<String>>();

		Map<String, List<String>> multiValueMap_Infrequent = new HashMap<String, List<String>>();

		String value, key;

		// Now the single column value filter need to be unfolded and the filter
		// with multiple values should be picked from the hbase table(hll,
		// minhash buffers)
		// combined into one by applying union operation on all the multiple
		// values...

		for (Entry<String, List<String>> entry : multiMap.entrySet()) {
			// filtering out the single filter values

			if (entry.getValue().size() == 1) {

				key = entry.getKey();
				value = entry.getValue().get(0);

				// boolean value_Isfrequent = checkFrequency(value_Tocheck);

				// adding to the frequent singleValueList_Frequent

				singleValueMap.put(key, value);

			}

			else {

				// deal with multiValuesFilter here

				multiValueMap.put(entry.getKey(), entry.getValue());

			}

		}

		segregateSingleValueFromMultipleValue(multiMap, singleValueMap, multiValueMap);

		// Now need to make a ordered query out of these according to the
		// order.txt.....

		Map<String, String> singleValueMapOrdered = getOrderedList(singleValueMap);

		String query;

		// this is the query which needs to be prepended with time buckets and
		// with appSecret so it can be looked up in hbase tqbles

		String appSecret = "33d1575713";
		String startDate = "01/01/2017";
		String endDate = "04/04/2017";

		Map<String, List<String>> timeBuckets = GetTimeBuckets.coarserTimeBuckets(startDate, endDate);

		List<String> dayList = timeBuckets.get("day");
		List<Long> daysUTC = GetTimeBuckets.daysInUTC(dayList);

		List<String> weekList = timeBuckets.get("week");
		List<Long> weekUTC = GetTimeBuckets.weeksInUTC(weekList);

		List<String> monthList = timeBuckets.get("month");
		List<Long> monthUTC = GetTimeBuckets.monthsInUTC(monthList);

		String searchKey;
		String tableName = null;
		boolean searchKey_Found = false;
		HLLBufferData aggregatedDayBuffer = getAggregatedData(appSecret, monthUTC, singleValueMapOrdered, multiValueMap,
				tableName);

	}

	private static HLLBufferData getAggregatedData(String appSecret, List<Long> bucketsUTC,
			Map<String, String> singleValueMapOrdered, Map<String, List<String>> multiValueMap, String tableName) {
		// TODO Auto-generated method stub

		List<HLLBufferData> masterHllBuffers = new ArrayList<HLLBufferData>();

		String searchKey;
		boolean searchKeyFound = false;

		for (long bucket : bucketsUTC) {
			List<HLLBufferData> hllBuffers = new ArrayList<HLLBufferData>();

			Map<String, String> singleValueMapFrequent = new HashMap<String, String>();
			Map<String, String> singleValueMapInfrequent = new HashMap<String, String>();
			// singleValueMap ordering should be done so that the freq and
			// infrequent map formed will be ordered..

			for (Entry<String, String> entry : singleValueMapOrdered.entrySet()) {

				searchKey = appSecret + "\t" + bucket + "\t" + entry.getKey() + ":" + entry.getValue();

				// check if the keys is present in the hbase table else if its a
				// ( month | week ) table break the bucket into 1 level down and
				// search in
				// that table and return the aggregated result...

				searchKeyFound = checkInHbase(searchKey, tableName);

				if (searchKeyFound) {

					singleValueMapFrequent.put(entry.getKey(), entry.getValue());
				}

				else {

					// this item is infrequent in this bucket... could be
					// frequent in the broken down buckets made from this
					// bucket.

					singleValueMapInfrequent.put(entry.getKey(), entry.getValue());
				}

			}

			// Collection<String> filterValuesFrequentOrdered =
			// singleValueMapFrequent;

			// List<String> duplicate_filterValues = new
			// ArrayList<String>(filterValuesFrequentOrdered);

			Map<String, String> singleValueMapFrequentCopy = new HashMap<String, String>();
			singleValueMapFrequentCopy.putAll(singleValueMapFrequent);

			while (singleValueMapFrequentCopy.size() != 0) {
				// this will be searched in hbase

				Joiner.MapJoiner joiner = Joiner.on(",").withKeyValueSeparator(":");
				String query = joiner.join(singleValueMapFrequentCopy);

				searchKey = appSecret + "+" + bucket + "+" + query;

				searchKeyFound = checkInHbase(searchKey, tableName);

				if (searchKeyFound) {

					byte[] byteArray = getFromHbase(searchKey, tableName);

					HLLBufferData buffer = new HLLBufferData(0.05).deserialize(byteArray);

					hllBuffers.add(buffer);

					// store the hbase result in some Variable which needs to be
					// intersected with others in this loop
					String[] toRemoveValues = query.split("\t");

					for (String removedValue : toRemoveValues) {

						String[] keyValuePair = removedValue.split(":");
						String key = keyValuePair[0];

						singleValueMapFrequentCopy.remove(key);

					}

				}

				else {

					// the size of seachKey needs to be reduced.. so the chances
					// of finding that in hbase increases...

					searchKey = reduceSearchKey(searchKey);

				}

			}

			// deal with the infrequent singleValueMapInfrequent here

			switch (tableName) {

			case "monthTable": {
				// need to break the bucket in week and then aggregate

				List<Long> weekUTC = GetTimeBuckets.monthToWeekBuckets(bucket);

				HLLBufferData hllBuffer = getAggregatedData(appSecret, weekUTC, singleValueMapInfrequent, null,
						"weekTable");

				hllBuffers.add(hllBuffer);

				break;
			}

			case "weekTable": {
				List<Long> weekUTC = GetTimeBuckets.weektoDayInUTC((bucket));

				HLLBufferData hllBuffer = getAggregatedData(appSecret, weekUTC, singleValueMapInfrequent, null,
						"dayTable");

				hllBuffers.add(hllBuffer);

				break;
			}

			case "dayTable": {

				// this item is infrequent even in days table.. need to search
				// in parquet file...

				break;
			}

			}

			for (Entry<String, List<String>> entry : multiValueMap.entrySet()) {

				String key = entry.getKey();
				List<String> values = entry.getValue();

				for (String value1 : values) {

					searchKey = appSecret + "\t" + bucket + "\t" + key + ":" + value1;
					searchKeyFound = checkInHbase(searchKey, tableName);

					if (searchKeyFound) {
						byte[] byteArray = getFromHbase(searchKey, tableName);
						// pick this hbase buffer and do union

						HLLBufferData buffer = new HLLBufferData(0.05).deserialize(byteArray);
						hllBuffers.add(buffer);

					}

					else {

						// break this bucket into lower buckets and search in
						// respective tale
						// ... do union of these buffers...
						
						

					}

				}

			}

			// do intersection of all the hll buffers and store it in master
			// hllBuufers

			HLLBufferData mergedHllBuffer = doIntersectionHllBuffers(hllBuffers);

			masterHllBuffers.add(mergedHllBuffer);

		}

		// do union of these buffers here across all the buckets in these UTC
		// List

		HLLBufferData hllBufferBuckets = doUnionHllBuffers(masterHllBuffers);

		return hllBufferBuckets;
	}

	private static HLLBufferData doUnionHllBuffers(List<HLLBufferData> masterHllBuffers) {
		// TODO Auto-generated method stub
		return null;
	}

	private static HLLBufferData doIntersectionHllBuffers(List<HLLBufferData> hllBuffers) {
		// TODO Auto-generated method stub
		return null;
	}

	private static byte[] getFromHbase(String searchKey, String tableName) {
		// TODO Auto-generated method stub

		Configuration dummyconf = new Configuration();

		dummyconf.set("hbase.zookeeper.quorum", "static.143.42.251.148.clients.your-server.de");
		dummyconf.set("hbase.zookeeper.property.clientPort", "2181");

		// Instantiating configuration class
		Configuration con = HBaseConfiguration.create(dummyconf);

		HTable table = null;
		try {
			table = new HTable(con, tableName);
		} catch (IOException e) {

			System.out.println("Error In Accessing Table");

			e.printStackTrace();
		}

		// Instantiating Get class
		Get g = new Get(Bytes.toBytes(searchKey));

		// Reading the data
		Result result = null;
		try {
			result = table.get(g);
		} catch (IOException e) {

			System.out.println("Error In Accessing rowKey Value");

			e.printStackTrace();
		}

		// Reading values from Result class object
		byte[] value = result.getValue(Bytes.toBytes("us"), Bytes.toBytes("hll"));

		return null;
	}

	private static void segregateSingleValueFromMultipleValue(Map<String, List<String>> multiMap,
			Map<String, String> singleValueMap, Map<String, List<String>> multiValueMap) {
		// TODO Auto-generated method stub

		String key, value;

		for (Entry<String, List<String>> entry : multiMap.entrySet()) {
			// filtering out the single filter values

			if (entry.getValue().size() == 1) {

				key = entry.getKey();
				value = entry.getValue().get(0);

				// boolean value_Isfrequent = checkFrequency(value_Tocheck);

				// adding to the frequent singleValueList_Frequent

				singleValueMap.put(key, value);

			}

			else {

				// deal with multiValuesFilter here

				multiValueMap.put(entry.getKey(), entry.getValue());

			}

		}

	}

	private static boolean checkInHbase(String searchKey, String hbaseTable) {
		// TODO Auto-generated method stub

		return false;
	}

	private static String reduceSearchKey(String searchKey) {
		// TODO Auto-generated method stub

		// reduce according to term frequency .. which will depend upon the keys
		// in

		return searchKey;
	}

	private static boolean checkSearchKey(String searchKey, Configuration config, String tableName) {
		// TODO Auto-generated method stub
		HTable table = null;
		try {
			table = new HTable(config, tableName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Get g = new Get(Bytes.toBytes(searchKey));

		Result result = null;
		try {
			result = table.get(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			System.out.println("difficulty in reading data from the row");
			e.printStackTrace();
		}

		byte[] value = result.getValue(Bytes.toBytes("buffer"), Bytes.toBytes("hllbuffer"));

		byte[] value1 = result.getValue(Bytes.toBytes("buffer"), Bytes.toBytes("minhashbuffer"));

		return false;

	}

	private static Map<String, List<Long>> getTimeBuckets(String startDate, String endDate) {
		// TODO Auto-generated method stub

		// List<String> timeBuckets = new ArrayList<String>();

		Map<String, List<Long>> utc_TimeBuckets = new HashMap<String, List<Long>>();

		List<Long> utc_dayList = new ArrayList<Long>();
		utc_dayList.add(1453401000000l);
		utc_TimeBuckets.put("day", utc_dayList);

		return utc_TimeBuckets;
	}

	private static Map<String, List<String>> fillTestData() {
		// TODO Auto-generated method stub

		Map<String, List<String>> multiMap = new HashMap<String, List<String>>();

		// change for location, phone...
		List<String> country = new ArrayList<String>();
		List<String> state = new ArrayList<String>();
		List<String> city = new ArrayList<String>();
		List<String> hierarchy_location = getHierarchialLocation(country, state, city);

		// multiMap.put("location", hierarchy_location);

		// put this hierarchy_location in multiMap.... and model details...

		List<String> location = new ArrayList<String>();

		location.add("Mumbai");

		multiMap.put("Location", location);

		// Adding filter 2... Age

		List<String> age = new ArrayList<String>();

		age.add("25");

		age.add("26");

		multiMap.put("Age", age);

		List<String> phone_Model = new ArrayList<String>();

		phone_Model.add("Xiomi");

		multiMap.put("Phone_Model", phone_Model);

		List<String> gender = new ArrayList<String>();

		gender.add("Male");

		multiMap.put("Gender", gender);

		List<String> events = new ArrayList<String>();

		events.add("click");
		events.add("Submit");

		multiMap.put("Event", events);

		List<String> networkType = new ArrayList<String>();

		networkType.add("4G");

		multiMap.put("NetworkType", networkType);

		return multiMap;
	}

	private static List<String> getHierarchialLocation(List<String> country, List<String> states, List<String> cities) {
		// TODO Auto-generated method stub

		List<String> hierarchialLocation = new ArrayList<String>();

		String location;
		String[] hierarchy;

		while (country.size() != 0) {

			for (String city : cities) {

				// for each city look into hbase table

				location = "India>Haryana>Gurugram";

				// now extract the state name out from the location list....

				hierarchialLocation.add(location);

				hierarchy = location.split(">");
				String statewith_City = hierarchy[1];

				states.remove(statewith_City);

			}

			for (String state : states) {
				// for each state look into the hbase table and extract the
				// value..

				location = "India>Goa";

				hierarchialLocation.add(location);
				hierarchy = location.split(">");

				String countrywith_State = hierarchy[1];

				country.remove(countrywith_State);

			}

		}

		return hierarchialLocation;

	}

	private static Map<String, String> getOrderedList(Map<String, String> singleValueList_Frequent) {
		// TODO Auto-generated method stub

		Map<String, String> ordered_singleValueList_Frequent = new HashMap<String, String>();

		Map<String, Integer> ordering = new HashMap<String, Integer>();

		ordering.put("Location", 4);
		ordering.put("Age", 3);
		ordering.put("Phone_Model", 1);
		ordering.put("Event", 2);
		ordering.put("NetworkType", 6);
		ordering.put("Gender", 5);

		// for ordering this map... first extract the key and order those key
		// according to the ordering in order.txt
		// then re write the list with the new order...

		Map<Integer, String> orderedFilter = new HashMap<Integer, String>();

		for (Entry<String, String> entry : singleValueList_Frequent.entrySet()) {

			// filterName_Rank.add(Map.Entry<ordering.get(entry.getKey()),
			// entry.getKey()>);

			orderedFilter.put(ordering.get(entry.getKey()), entry.getKey());

		}

		// the filterName has now been sorted by key values that has been picked
		// by the ordering module....
		Set<Entry<Integer, String>> filterName_Rank = orderedFilter.entrySet();

		for (Entry<Integer, String> entry : filterName_Rank) {

			ordered_singleValueList_Frequent.put(entry.getValue(), singleValueList_Frequent.get(entry.getValue()));

		}

		return ordered_singleValueList_Frequent;
	}

	private static boolean checkFrequency(String value_Tocheck) {
		// TODO Auto-generated method stub

		return true;
	}

}
