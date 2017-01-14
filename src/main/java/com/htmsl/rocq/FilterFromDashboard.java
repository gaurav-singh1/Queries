package com.htmsl.rocq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Collections;

public class FilterFromDashboard {

	public static String DELIMITER_QUERY = "_";

	public static void main(String[] args) {

		// *******update the multimap : completely unfold the country,state,
		// region to finally make values corresponding to location key ...

		// India->Haryana->Gurugram,,, like this,,,
		// Location;{India->Haryana->Gurugram,.....,.....}

		// Similarly do for Model, Manufacturer, and Os....

		Map<String, List<String>> multiMap = fillTestData();

		System.out.println("Multimap is=" + multiMap.toString());

		Map<String, String> singleValueList_Frequent = new HashMap<String, String>();

		Map<String, String> singleValueList_Infrequent = new HashMap<String, String>();

		Map<String, List<String>> multiValueList_Frequent = new HashMap<String, List<String>>();

		Map<String, List<String>> multiValueList_Infrequent = new HashMap<String, List<String>>();

		String value_Tocheck, key;

		// Now the single column value filter need to be unfolded and the filter
		// with multiple values should be
		// combined into one by applying union operation on all the multiple
		// values...

		for (Entry<String, List<String>> entry : multiMap.entrySet()) {
			// filtering out the single filter values

			if (entry.getValue().size() == 1) {

				key = entry.getKey();
				value_Tocheck = entry.getValue().get(0);

				boolean value_Isfrequent = checkFrequency(value_Tocheck);

				if (value_Isfrequent) {
					// adding to the frequent singleValueList_Frequent

					singleValueList_Frequent.put(key, value_Tocheck);

				}

				else {

					// adding to the frequent singleValueList_Infrequent

					singleValueList_Infrequent.put(key, value_Tocheck);

				}

			}

			else {

				// deal with multiValuesFilter here

				// do union operation for each value in this filterName

				// MasterResult of this

				// Result

			}

		}

		// Now need to make a ordered query out of these according to the
		// order.txt.....

		Map<String, String> singleValueList_Frequent_Ordered = getOrderedList(singleValueList_Frequent);

		System.out.println(singleValueList_Frequent.toString());

		Collection<String> filterValues_Ordered = singleValueList_Frequent_Ordered
				.values();

		String query;

		// this is the query which needs to be appended with time buckets and
		// pre pended with appSecret so it can be looked up in hbase tqbles

		String appSecret = "33d1575713";
		String startDate = "mm/dd/yyyy";
		String endDate = "mm/dd/yyyy";

		List<String> timeBuckets = getTimeBuckets(startDate, endDate);

		// Now Making the final query to lookup in hbase...

		String searchKey;

		for (String bucket : timeBuckets) {

			// ********* check which Hbase Table(day, week, Month to
			// use)**********

			List<String> duplicate_filterValues = new ArrayList<String>(
					filterValues_Ordered);

			while (duplicate_filterValues.size() != 0) {
				// this will be searched in hbase

				query = String.join(DELIMITER_QUERY, duplicate_filterValues);
				searchKey = appSecret + "+" + query + "+" + bucket;

				System.out.println(searchKey);

				boolean searchKey_Found = checkSearchKey(searchKey);

				if (searchKey_Found) {

					// do the need full...search in hbase
					// and aggregate the result ...store in some data
					// structure..

					// MasterResult

					// Result... do intersection betweeen these two...

					// now remove the searchKey from the
					// duplicate_filterValues... for which the results have been
					// computed

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

		}

	}

	private static String reduceSearchKey(String searchKey) {
		// TODO Auto-generated method stub

		// reduce according to term frequency .. which will depend upon the keys
		// in

		return searchKey;
	}

	private static boolean checkSearchKey(String searchKey) {
		// TODO Auto-generated method stub
		return false;
	}

	private static List<String> getTimeBuckets(String startDate, String endDate) {
		// TODO Auto-generated method stub

		List<String> timeBuckets = new ArrayList<String>();

		timeBuckets.add("25_Feb_2016");

		return timeBuckets;
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
