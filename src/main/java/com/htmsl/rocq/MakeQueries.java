package com.htmsl.rocq;

import com.htmsl.rocq.configuration.HbaseConfigurationConstants;

import org.apache.hadoop.hbase.client.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Collections;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.util.Bytes;

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
		// with multiple values should be
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

				// do union operation for each value in this filterName

				// MasterResult of this

				// Result

				multiValueMap.put(entry.getKey(), entry.getValue());

			}

		}

		// Now need to make a ordered query out of these according to the
		// order.txt.....

		Map<String, String> singleValueMap_Ordered = getOrderedList(singleValueMap);

		System.out.println(singleValueMap.toString());

		String query;

		// this is the query which needs to be prepended with time buckets and
		// with appSecret so it can be looked up in hbase tqbles

		String appSecret = "33d1575713";
		String startDate = "mm/dd/yyyy";
		String endDate = "mm/dd/yyyy";

		Map<String, List<Long>> timeBuckets = getTimeBuckets(startDate, endDate);

		// Now Making the final query to lookup in hbase...

		String searchKey;

		// ********* check which Hbase Table(day, week, Month to
		// use)**********

		List<Long> utc_dayList = timeBuckets.get("day");
		List<Long> utc_weekList = timeBuckets.get("week");
		List<Long> utc_monthList = timeBuckets.get("month");

		// making queries to be looked in Hbase_Day Table..the result will be
		// hbase objects

		boolean searchKey_Found;

		//
		Configuration conf = getHbaseConnection();

		// HTable hTable = new HTable(hConf, tableName);

		for (long dayBucket : utc_dayList) {

			String tableName = "DayWise";

			// segregate frequent and infrequent items...for both single value
			// list

			// singleValueMap ordering should be done so that the freq and
			// infreq map formed will be ordered..

			for (Entry<String, String> entry : singleValueMap_Ordered
					.entrySet()) {

				searchKey = appSecret + "+" + dayBucket + "+"
						+ entry.getValue();

				// check if this key is present in hbase else put this key in
				// singleValue_InfrequentMap

				searchKey_Found = checkSearchKey(searchKey, conf, tableName);

				if (searchKey_Found) {

					singleValueMap_Frequent.put(entry.getKey(),
							entry.getValue());
				}

				else {

					singleValueMap_Infrequent.put(entry.getKey(),
							entry.getValue());
				}

			}

			// now the single value map is obtained .... with differentiation on
			// where to find these ....either hbase or parquet...

			Collection<String> filterValues_Frequent_Ordered = singleValueMap_Frequent
					.values();

			List<String> duplicate_filterValues = new ArrayList<String>(
					filterValues_Frequent_Ordered);

			while (duplicate_filterValues.size() != 0) {
				// this will be searched in hbase

				query = String.join(DELIMITER_QUERY, duplicate_filterValues);
				searchKey = appSecret + "+" + dayBucket + "+" + query;

				searchKey_Found = checkInHbase(searchKey, tableName);

				if (searchKey_Found) {

					// store the hbase result in some Variable which needs to be
					// intersected with others in this loop
					String[] toRemoveValues = query.split(DELIMITER_QUERY);

					for (String removedValue : toRemoveValues) {

						// modifying the duplicate_filterValues...
						duplicate_filterValues.remove(removedValue);

					}

				}

				else {

					// the size of seachKey needs to be reduced.. so the chances
					// of finding that in hbase increases...

					searchKey = reduceSearchKey(searchKey);

				}

			}

			// similarly find the results of infrequent single val keys in
			// parquet... and store in some Variable...

			for (Entry<String, List<String>> entry : multiValueMap.entrySet()) {

				key = entry.getKey();
				List<String> values = entry.getValue();

				for (String value1 : values) {

					searchKey = appSecret + "+" + dayBucket + "+" + value1;
					searchKey_Found = checkSearchKey(searchKey, conf, tableName);

					if (searchKey_Found) {

						// pick this hbase buffer and do union

					}

					else {

						// search this in parquet... do

					}

				}

				// for this key do union of frequent and infrequent values store
				// in multiVal({"location", union of all the buffers},,,,,.....)

			}

		}

	}

	private static Configuration getHbaseConnection() {
		// TODO Auto-generated method stub

		Configuration dummyconf = new Configuration();

		dummyconf.addResource("/etc/hbase/conf/hbase-site.xml");
		dummyconf.set("hbase.zookeeper.quorum",
				"static.232.40.46.78.clients.your-server.de");
		dummyconf.set("hbase.zookeeper.property.clientPort", "2181");
		dummyconf.set("zookeeper.znode.parent", "/hbase-unsecure");
		Configuration conf = HBaseConfiguration.create(dummyconf);

		return conf;
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

	private static boolean checkSearchKey(String searchKey,
			Configuration config, String tableName) {
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

		byte[] value = result.getValue(Bytes.toBytes("buffer"),
				Bytes.toBytes("hllbuffer"));

		byte[] value1 = result.getValue(Bytes.toBytes("buffer"),
				Bytes.toBytes("minhashbuffer"));

		return false;

	}

	private static Map<String, List<Long>> getTimeBuckets(String startDate,
			String endDate) {
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
		List<String> hierarchy_location = getHierarchialLocation(country,
				state, city);

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

	private static List<String> getHierarchialLocation(List<String> country,
			List<String> states, List<String> cities) {
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

	private static Map<String, String> getOrderedList(
			Map<String, String> singleValueList_Frequent) {
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

			ordered_singleValueList_Frequent.put(entry.getValue(),
					singleValueList_Frequent.get(entry.getValue()));

		}

		return ordered_singleValueList_Frequent;
	}

	private static boolean checkFrequency(String value_Tocheck) {
		// TODO Auto-generated method stub
		return true;
	}

}
