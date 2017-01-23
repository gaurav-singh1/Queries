package com.htmsl.rocq;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.simple.JSONObject;
import org.apache.hadoop.hbase.client.Result;

public class ToInsertCode {

	/*
	 
	 
	 
	 // Instantiating HTable class
      HTable table = new HTable(config, "emp");

      // Instantiating Get class
      Get g = new Get(Bytes.toBytes("row1"));

      // Reading the data
      Result result = table.get(g);

      // Reading values from Result class object
      byte [] value = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("name"));

      byte [] value1 = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("city"));

	 
	 
	 
	 
	 
	 
	 */
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		
		
		Configuration dummyconf = new Configuration();

		//dummyconf.addResource("/etc/hbase/conf/hbase-site.xml");
		dummyconf.set("hbase.zookeeper.quorum",
				"static.232.40.46.78.clients.your-server.de");
		dummyconf.set("hbase.zookeeper.property.clientPort", "2181");
		dummyconf.set("zookeeper.znode.parent", "/hbase-unsecure");
		Configuration conf = HBaseConfiguration.create(dummyconf);
		
		try {
			org.apache.hadoop.hbase.client.Connection conn=ConnectionFactory.createConnection(conf);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String tableName="XYZ";
		
		try {
			
			
			HTable table = new HTable(conf, "XYZ");
			
			// Instantiating Get class
		      Get g = new Get(Bytes.toBytes("1+2"));
		      
		   // Reading the data
		      Result result = table.get(g);
			
		      
		   // Reading values from Result class object
		      byte [] value = result.getValue(Bytes.toBytes("CF"),Bytes.toBytes("var_a"));

		      System.out.println("result is=");
		      String name = Bytes.toString(value);
		      
		      
		      
		      
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
}
