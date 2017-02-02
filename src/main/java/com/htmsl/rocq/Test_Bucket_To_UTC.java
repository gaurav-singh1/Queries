package com.htmsl.rocq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test_Bucket_To_UTC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Map<String, List<Integer>> timeBucketstoUTC=new HashMap<String, List<Integer>>();
		
		
		String startDate="01-01-2016";
		String endDate="26-03-2016";
		
		
		Map<String, List<String>> timeBuckets=GetTimeBuckets.coarserTimeBuckets(startDate, endDate);
		
		System.out.println(GetTimeBuckets.coarserTimeBuckets(startDate, endDate).toString());
		
		
		
		
		List<String> dayList=timeBuckets.get("day");
		List<Long> daysUTC=GetTimeBuckets.daysInUTC(dayList);
		
		List<String> weekList=timeBuckets.get("week");
		List<Long> weekUTC=GetTimeBuckets.weeksInUTC(weekList);
		
		List<String> monthList=timeBuckets.get("month");
		List<Long> monthUTC=GetTimeBuckets.monthsInUTC(monthList);
		
		
		
		System.out.println("^^^^^^^^^^^^all day buckets^^^^^^^^^^^^^");
		
	//	System.out.println(GetTimeBuckets.daysInUTC(dayList).toString());
		
		System.out.println(GetTimeBuckets.allDaysBuckets("01_01_2016", "03_02_2016"));
		
		
		//there will be two function that will do the following:
		//1. convert the day into UTC 
		//2. convert the List<day> into UTC List...
		
		
		
		
		//long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;

		System.out.println("===================================");
		
		
		long millis=GetTimeBuckets.monthInUTC("03_2016");
		System.out.println(millis);
		
		System.out.println("===================================");
		
		System.out.println(GetTimeBuckets.monthToWeekBuckets(1422729000000l).toString());
		
		System.out.println("=================================");
		
		System.out.println(GetTimeBuckets.weektoDayInUTC(1425148200000l).toString());

	}

}
