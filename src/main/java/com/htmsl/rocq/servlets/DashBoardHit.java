package com.htmsl.rocq.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.*;

public class DashBoardHit {

	public static String StartTime = "startTime";
	public static String EndTime = "endTime";
	public static String AppSecret = "appSecret";
	public static String Cards = "Cards";
	public static String granularity = "gran";
	public static String metric = "metric";
	public static String filters = "filters";
	public static String userType = "userType";

	public static String Mapper_Frontend_Backend_Variables(String var) {

		String mappedVal;

		Map<String, String> map = new HashMap<String, String>();

		map.put("nu", "newUsers");

		map.put("au", "activeUsers");

		map.put("ru", "returningUsers");

		map.put("un", "uninstalls");

		map.put("gran", "grainularity");

		map.put("metric", "metric");

		map.put("filters", "filters");

		map.put("userType", "userType");

		mappedVal = map.get(var);

		return mappedVal;

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// parsing the request objects... will contain the list of cards....

		
		
		
		
		//making a map from the request parameters...
		
		Map<String, List<String>> datapacket=new HashMap<String, List<String>>();
		
		
		
		
		
		
		
		
		
		
		JSONObject appSecret_packet = (JSONObject) request.getAttribute(AppSecret);
		JSONObject startTime_packet = (JSONObject) request.getAttribute(StartTime);
		JSONObject endTime_packet = (JSONObject) request.getAttribute(EndTime);
		JSONObject cards_packet = (JSONObject) request.getAttribute(Cards);

		String appSecret=(String) appSecret_packet.get("value");
		String startTime=(String) startTime_packet.get("value");
		String endTime=(String) endTime_packet.get("value");
		JSONObject filters_packet=(JSONObject) cards_packet.get("filter");
		
		
		
		
		
		
		

		int i;

		for (i = 0; i < filters_packet.size() - 1; i++) {

			// Json Card received...
			JSONObject card = (JSONObject) filters_packet.get(i);

			String grainularity = (String) card.get(DashBoardHit.granularity);
			String metric = (String) card.get(DashBoardHit.metric);

		}

	}

}
