package com.htmsl.rocq.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Result;

@WebServlet("/GetDeckFiltersKeys")

public class GetDeckFiltersKeys extends HttpServlet {

	private static final String AppSecret = "appSecret";
	private static final String CardId = "cardId";
	private static final String DeckId = "deckId";
	public static String FilterKeys_Table = "filterKeys";
	public static String cardId = "some card id";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// this basically looks up in the hbase table and returns according to
		// the following....

		// the global filter keys depends upon the appSecret...

		// the card filter keys depends upon the card id, deck id, and the
		// appSecret.

		String appSecret = request.getParameter(AppSecret);
		String deckId = request.getParameter(DeckId);
		// String cardId=request.getParameter(CardId);

		String searchKey = appSecret + " " + deckId;

		// get the space separated filter keys

		String filterKeys=searchKey;
	//	String filterKeys = getFilterKeysFromHbase(searchKey);
		
		
		//response.setContentType("application/json");
		
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(filterKeys);
	    
	    response.getWriter().flush();
	    response.getWriter().close();

	}

	private String getFilterKeysFromHbase(String searchKey) throws IOException {
		// TODO Auto-generated method stub

		System.out.println("Hello");

		Configuration dummyconf = new Configuration();

		dummyconf.addResource("/etc/hbase/conf/hbase-site.xml");
		// dummyconf.set("hbase.zookeeper.quorum","static.143.42.251.148.clients.your-server.de");

		dummyconf.set("hbase.zookeeper.quorum", "static.232.40.46.78.clients.your-server.de");
		dummyconf.set("hbase.zookeeper.property.clientPort", "2181");
		dummyconf.set("zookeeper.znode.parent", "/hbase-unsecure");
		Configuration conf = HBaseConfiguration.create(dummyconf);

		// HTable table = new HTable(conf, "rocq-hourly");

		HTable table = new HTable(conf, "Test_FilterKeys");

		// Instantiating Get class
		// Get g = new Get(Bytes.toBytes("55e8b96eafa8 0 AU
		// deviceInfo.manufacturer=Sony Ericsson"));

		Get g = new Get(Bytes.toBytes("1234567 overview"));

		// Reading the data
		Result result = table.get(g);

		// Reading values from Result class object
		// byte[] value =
		// result.getValue(Bytes.toBytes("us"),Bytes.toBytes("hll"));
		byte[] value = result.getValue(Bytes.toBytes("keys"),Bytes.toBytes("value"));

		// System.out.println("result is="+value.toString());

		String filterKeys = Bytes.toString(value);
		// System.out.println("result is="+Bytes.toString(value));
		table.close();

		return filterKeys;
	}

}
