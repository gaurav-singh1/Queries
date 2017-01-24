package com.htmsl.rocq;



import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class hbaseConnectionCode {
	public static void main(String args[]) throws IOException
	{
		Configuration dummyconf = new Configuration();

		dummyconf.addResource("/etc/hbase/conf/hbase-site.xml");
		dummyconf.set("hbase.zookeeper.quorum",
				"static.232.40.46.78.clients.your-server.de");
		dummyconf.set("hbase.zookeeper.property.clientPort","2181");
		dummyconf.set("zookeeper.znode.parent", "/hbase-unsecure");
		Configuration conf=  HBaseConfiguration.create(dummyconf);



		HTable table = new HTable(conf, "XYZ");

		// Instantiating Get class
		Get g = new Get(Bytes.toBytes("1+2"));

		// Reading the data
		Result result = table.get(g);

		// Reading values from Result class object
		byte[] value = result.getValue(Bytes.toBytes("CF"),
				Bytes.toBytes("var_a"));

		System.out.println("result is="+Bytes.toString(value));
		table.close();

	}

}