package com.htmsl.rocq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test_Bucket_To_UTC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		Map<String, List<Integer>> timeBucketstoUTC=new HashMap<String, List<Integer>>();
		
		
		String startDate="28-03-2016";
		String endDate="15-04-2016";
		
		
		Map<String, List<String>> timeBuckets=GetTimeBuckets.coarserTimeBuckets(startDate, endDate);
		
		System.out.println(GetTimeBuckets.coarserTimeBuckets(startDate, endDate).toString());
		
		
		
		for(Entry<String, List<String>> list : timeBuckets.entrySet()){
			
			
			
			
		}
		
		
		
		//long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;

		
		
		
		
		

	}

}
