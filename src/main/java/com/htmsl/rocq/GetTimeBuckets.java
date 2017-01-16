package com.htmsl.rocq;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.json.simple.JSONObject;

public class GetTimeBuckets {

	public static Map<String, List<String>> coarserTimeBuckets(
			String startDate, String endDate) {
		// TODO Auto-generated method stub

		Map<String, List<String>> timeBuckets_H = new HashMap<String, List<String>>();

		List<String> weekList = new ArrayList<String>();
		// these parameters are received from the dashboard
		String date1 = startDate;
		String date2 = endDate;

		DateFormat formater = new SimpleDateFormat("dd-MM-yyyy");

		DateFormat formater1 = new SimpleDateFormat("MM_yyyy");
		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();

		// list of weeks , days and months buckets
		List<String> monthList = new ArrayList<String>();
		List<String> weeksList = new ArrayList<String>();
		List<String> daysList = new ArrayList<String>();

		// monthList.add("DEC_2016");
		// System.out.println(monthList.toString());

		String[] startDate_parts = date1.split("-");
		String[] endDate_parts = date2.split("-");

		int day_startDate = Integer.parseInt(startDate_parts[0]);
		int day_endDate = Integer.parseInt(endDate_parts[0]);

		System.out.println(day_startDate + "    " + day_endDate);

		int month_startDate = Integer.parseInt(startDate_parts[1]);
		int month_endDate = Integer.parseInt(endDate_parts[1]);

		System.out.println(month_startDate + "    " + month_endDate);

		int year_startDate = Integer.parseInt(startDate_parts[2]);
		int year_endDate = Integer.parseInt(endDate_parts[2]);

		System.out.println(year_startDate + "    " + year_endDate);

		Calendar mycal_startDate = new GregorianCalendar(year_startDate,
				month_startDate, 0);
		Calendar mycal_endDate = new GregorianCalendar(year_endDate,
				month_endDate, 0);
		int daysInMonth_startDate = mycal_startDate
				.getActualMaximum(Calendar.DAY_OF_MONTH);
		int daysInMonth_endDate = mycal_endDate
				.getActualMaximum(Calendar.DAY_OF_MONTH);

		System.out.println(daysInMonth_startDate);

		String dummy_date2 = date2;

		if (day_startDate > day_endDate) {

			// for adding the last month to the month list if the start day is
			// more than the end day of endDate
			dummy_date2 = daysInMonth_endDate + "-" + month_endDate + "-"
					+ year_endDate;
		}

		try {
			beginCalendar.setTime(formater.parse(date1));
			finishCalendar.setTime(formater.parse(dummy_date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		while (beginCalendar.before(finishCalendar)) {
			// add one month to date per loop
			String date = formater1.format(beginCalendar.getTime())
					.toUpperCase();
			// System.out.println(date);
			monthList.add(date);
			beginCalendar.add(Calendar.MONTH, 1);
		}

		System.out.println(monthList.toString());

		String startMonth = "";
		if (day_startDate != 1) {
			startMonth = monthList.get(0);
			monthList.remove(0);

			System.out.println("startMonth is=" + startMonth);
		}

		if ((year_startDate == year_endDate)
				&& (month_startDate == month_endDate)
				&& (day_endDate - day_startDate != daysInMonth_startDate - 1)
				&& monthList.size() != 0) {
			startMonth = monthList.get(0);
			monthList.remove(0);

			System.out.println("startMonth is=" + startMonth);

		}

		String lastMonth = "";

		if (day_endDate != daysInMonth_endDate && monthList.size() != 0) {

			int size = monthList.size();
			lastMonth = monthList.get(size - 1);
			monthList.remove(size - 1);

			System.out.println("lastMonth is=" + lastMonth);
		}

		System.out.println(monthList.toString());
		int i = 0;

		// making weeks and days in the endDate month...if its not a whole month

		if (day_endDate != daysInMonth_endDate
				&& month_startDate != month_endDate) {

			System.out
					.println("Control in making weeks and days in last month if its not a whole month");
			int no_of_weeks = day_endDate / 7;

			for (i = 1; i <= no_of_weeks; i++) {
				weekList.add("week_" + i + "_" + lastMonth);
			}

			// remaining days of the last month...
			int start_Remaining_Days = 7 * no_of_weeks + 1;

			for (i = start_Remaining_Days; i <= day_endDate; i++) {

				daysList.add(i + "_" + lastMonth);

			}
		}

		int limit = 0;

		// getting the weeks list and days list in the starting month if its not
		// a whole month...
		// meaning the startMonth date is removed
		if (startMonth.length() > 2) {

			System.out.println("Control in startMonth making weeks and days");
			if (month_startDate == month_endDate
					&& year_startDate == year_endDate) {

				if (day_endDate - day_startDate < 6) {
					for (i = day_startDate; i <= day_endDate; i++) {
						daysList.add(i + "_" + startMonth);
					}

				} else {

					if (day_startDate == 1 && day_endDate >= 7)
						weekList.add("week_1_" + startMonth);

					else {
						limit = day_endDate > 7 ? 7 : day_endDate;
						for (i = day_startDate; i <= limit; i++) {
							daysList.add(i + "_" + startMonth);
						}

					}

					if (day_startDate <= 8)
						day_startDate = 8;

					if (day_startDate == 8 && day_endDate >= 14
							&& day_startDate <= day_endDate)
						weekList.add("week_2_" + startMonth);

					else {
						limit = day_endDate > 14 ? 14 : day_endDate;
						for (i = day_startDate; i <= limit; i++) {
							daysList.add(i + "_" + startMonth);
						}

					}

					if (day_startDate <= 15)
						day_startDate = 15;

					if (day_startDate == 15 && day_endDate >= 22
							&& day_startDate <= day_endDate)
						weekList.add("week_3_" + startMonth);

					else {
						limit = day_endDate > 21 ? 21 : day_endDate;

						for (i = day_startDate; i <= limit; i++) {
							daysList.add(i + "_" + startMonth);
						}

					}

					if (day_startDate <= 22)
						day_startDate = 22;

					if (day_startDate == 22 && day_endDate >= 28
							&& day_startDate <= day_endDate)
						weekList.add("week_4_" + startMonth);

					else {
						limit = day_endDate > 28 ? 28 : day_endDate;

						for (i = day_startDate; i <= limit; i++) {
							daysList.add(i + "_" + startMonth);
						}

					}

					if (day_startDate <= 29)
						day_startDate = 29;

					if (day_startDate == 29
							&& day_endDate >= daysInMonth_startDate
							&& day_startDate <= day_endDate)
						weekList.add("week_5_" + startMonth);

					else {

						for (i = day_startDate; i <= day_endDate; i++) {
							daysList.add(i + "_" + startMonth);
						}

					}

				}

			} else {

				day_endDate = daysInMonth_startDate;

				if (day_startDate == 1 && day_endDate >= 7)
					weekList.add("week_1_" + startMonth);

				else {
					limit = day_endDate > 7 ? 7 : day_endDate;
					for (i = day_startDate; i <= limit; i++) {
						daysList.add(i + "_" + startMonth);
					}

				}

				if (day_startDate <= 8)
					day_startDate = 8;

				if (day_startDate == 8 && day_endDate >= 14
						&& day_startDate <= day_endDate)
					weekList.add("week_2_" + startMonth);

				else {
					limit = day_endDate > 14 ? 14 : day_endDate;
					for (i = day_startDate; i <= limit; i++) {
						daysList.add(i + "_" + startMonth);
					}

				}

				if (day_startDate <= 15)
					day_startDate = 15;

				if (day_startDate == 15 && day_endDate >= 22
						&& day_startDate <= day_endDate)
					weekList.add("week_3_" + startMonth);

				else {
					limit = day_endDate > 21 ? 21 : day_endDate;

					for (i = day_startDate; i <= limit; i++) {
						daysList.add(i + "_" + startMonth);
					}

				}

				if (day_startDate <= 22)
					day_startDate = 22;

				if (day_startDate == 22 && day_endDate >= 28
						&& day_startDate <= day_endDate)
					weekList.add("week_4_" + startMonth);

				else {
					limit = day_endDate > 28 ? 28 : day_endDate;

					for (i = day_startDate; i <= limit; i++) {
						daysList.add(i + "_" + startMonth);
					}

				}

				if (day_startDate <= 29)
					day_startDate = 29;

				if (day_startDate == 29 && day_endDate >= daysInMonth_startDate
						&& day_startDate <= day_endDate)
					weekList.add("week_5_" + startMonth);

				else {

					for (i = day_startDate; i <= day_endDate; i++) {
						daysList.add(i + "_" + startMonth);
					}

				}

			}

		}

		// printing the buckets...

		// System.out.println(daysList.toString());
		//
		// System.out.println(weekList.toString());
		//
		// System.out.println(monthList.toString());

		timeBuckets_H.put("day", daysList);
		timeBuckets_H.put("week", weekList);
		timeBuckets_H.put("month", monthList);

		return timeBuckets_H;

	}

	/**
	 * converts a single date into UTC at 00:00:00 hours
	 * 
	 * @param date
	 * @return
	 */
	public static long dayInUTC(String date) {

		long utc_Time = 0;

		try {
			date =date+" 00:00:00";
			utc_Time = new SimpleDateFormat("dd_mm_yyyy HH:mm:ss").parse(date)
					.getTime();

		} catch (ParseException e) {
			// TODO Auto-generated catch block

			System.out.println("Couldn't convert Date to Utc");
			e.printStackTrace();
		}

		return utc_Time;

	}

	/**
	 * converts list of dates in UTC time at 00:00:00 hours
	 * 
	 * @param dates
	 * @return
	 */
	public static List<Long> daysInUTC(List<String> dates) {

		long utc_Time = 0;

		List<Long> daysInUTC = new ArrayList<Long>();

		for (String date : dates) {

			try {
				
				date=date+" 00:00:00";
				utc_Time = new SimpleDateFormat("dd_mm_yyyy HH:mm:ss").parse(
						date).getTime();

				daysInUTC.add(utc_Time);

			} catch (ParseException e) {
				// TODO Auto-generated catch block

				System.out.println("Couldn't convert Date to Utc");
				e.printStackTrace();
			}

		}

		return daysInUTC;

	}

	public static long weekInUTC(String week) {

		// converting each week to first day of week -> that can be converted to
		// utc by dayInUTC func().

		String[] parsedWeek = week.split("_");

		int weekNo = Integer.parseInt(parsedWeek[1]);
		String month = parsedWeek[2];

		String year = parsedWeek[3];

		int day = 7 * (weekNo - 1) + 1;

		String date = day + "_" + month + "-" + year;

		long utc_Time = dayInUTC(date);

		return utc_Time;

	}

	/**
	 * At first converts a week list into day List(the first day in the week)
	 * and then converts this list into UTC time list at 00:00:00 hours
	 * 
	 * @param weekList
	 * @return
	 */
	public static List<Long> weeksInUTC(List<String> weekList) {

		List<Long> utc_Times = null;

		List<String> dayList = new ArrayList<String>();

		// converting each week to first day of week -> that can be converted to
		// utc by dayInUTC func().
		for (String week : weekList) {

			String[] parsedWeek = week.split("_");

			int weekNo = Integer.parseInt(parsedWeek[1]);
			String month = parsedWeek[2];

			String year = parsedWeek[3];

			int day = 7 * (weekNo - 1) + 1;

			String date = day + "_" + month + "-" + year;

			dayList.add(date);

		}

		utc_Times = daysInUTC(dayList);
		return utc_Times;

	}

	public static long monthInUTC(String month) {

		long utc_Time = 0;

		String date = "01" + "_" + month;

		utc_Time = dayInUTC(date);

		return utc_Time;

	}

	public static List<Long> monthsInUTC(List<String> months) {

		List<Long> utc_Times = new ArrayList<Long>();
		long utc_Time;
		String date;
		for (String month : months) {

			date = "01" + "_" + month;

			utc_Time = dayInUTC(date);
			utc_Times.add(utc_Time);

		}

		return utc_Times;

	}
	
	
	public static List<String> allDaysBuckets(String sDate, String eDate){
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
		List<String> dayList=new ArrayList<String>();
		
		LocalDate startDate=LocalDate.parse(sDate, formatter);
		LocalDate endDate=LocalDate.parse(eDate, formatter);

		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
	       
			dayList.add(date.toString());
	    }
		
		return dayList;
	}

}
