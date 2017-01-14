package com.htmsl.rocq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UnfoldFilters {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Receives a List of map of filters from the dashboard to be
		// unfolded...

		Map<String, List<String>> multiMap = new HashMap<String, List<String>>();

		List<String> location = new ArrayList<String>();

		location.add("Delhi");

		location.add("Agra");

		location.add("Banglore");

		location.add("Mumbai");


		multiMap.put("Location", location);

		// Adding filter 2... Age

		List<String> age = new ArrayList<String>();

		age.add("25");

		age.add("26");

		age.add("27");

		age.add("28");

		multiMap.put("Age", age);

		List<String> phone_Model = new ArrayList<String>();

		phone_Model.add("iphone");

		phone_Model.add("Samsumg");

		phone_Model.add("Lenovo");

		phone_Model.add("Xiomi");

		multiMap.put("Phone_Model", phone_Model);

		List<String> events = new ArrayList<String>();

		events.add("click");
		events.add("Submit");
		events.add("sign up");
		events.add("swipe up");

		multiMap.put("Event", events);

		System.out.println("Multimap is="+multiMap.toString());

		String appSecret = "33d157576";

		Map<String, Integer> hm = new HashMap<String, Integer>();

		hm.put("Location", 4);
		hm.put("Age", 3);
		hm.put("Phone_Model", 1);
		hm.put("Event", 2);

		 System.out.println(hm.entrySet());

		Set<Entry<String, Integer>> entries = hm.entrySet();

		Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String, Integer>>() {

			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				Integer v1 = e1.getValue();
				Integer v2 = e2.getValue();

				return v1.compareTo(v2);

			}

		};

		List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>(entries);

		// sorting HashMap by values using comparator
		Collections.sort(listOfEntries, valueComparator);

		List<String> filters = new ArrayList<String>();

		for (Entry<String, Integer> entry : listOfEntries) {

			filters.add(entry.getKey());
		}

		System.out.println(filters.size() + "+" + filters.toString());

		UnfoldFilters obj = new UnfoldFilters();

		List<String> unfolded_filter = obj.unfold_Filter("", multiMap, new ArrayList<String>(), filters, 0);

		System.out.println(unfolded_filter.size() + "+" + unfolded_filter.toString());

	}

	public List<String> unfold_Filter(String filter, Map<String, List<String>> multiMap, List<String> filters,
			List<String> ordered_Attribute, int filterNo) {

		if (filterNo >= ordered_Attribute.size()) {

			filters.add(filter);
			return filters;

		}

		else {

			String attribute = (String) ordered_Attribute.get(filterNo);

			List<String> somefilterValues = multiMap.get(attribute);

			for (int i = 0; i < somefilterValues.size(); i++) {

				unfold_Filter(filter + "+" + somefilterValues.get(i), multiMap, filters, ordered_Attribute,
						filterNo + 1);
			}

		}

		return filters;

	}

}
