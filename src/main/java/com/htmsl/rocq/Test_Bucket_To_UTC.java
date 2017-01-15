package com.htmsl.rocq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test_Bucket_To_UTC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		Map<String, List<Integer>> timeBucketstoUTC=new HashMap<String, List<Integer>>();
		
		
		String startDate="01-01-2017";
		String endDate="26-03-2017";
		
		
		Map<String, List<String>> timeBuckets=GetTimeBuckets.coarserTimeBuckets(startDate, endDate);
		
		System.out.println(GetTimeBuckets.coarserTimeBuckets(startDate, endDate).toString());
		
		
		
		List<String> dayList=timeBuckets.get("day");
		
		List<String> weekList=timeBuckets.get("week");
		
		List<String> monthList=timeBuckets.get("month");
		
		
		
		System.out.println("^^^^^^^^^^^^--------------^^^^^^^^^^^^^");
		
	//	System.out.println(GetTimeBuckets.dayInUTC("28_02_2017 00:00:00"));
		
		//there will be two function that will do the following:
		//1. convert the day into UTC 
		//2. convert the List<day> into UTC List...
		
		
		
		
		//long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;

		
		
		
		
		

	}

}
