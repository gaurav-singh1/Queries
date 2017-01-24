package com.htmsl.rocq;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class ToInsertCode {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		Configuration dummyconf = new Configuration();

		dummyconf.addResource("/etc/hbase/conf/hbase-site.xml");
		dummyconf.set("hbase.master",
				"static.232.40.46.78.clients.your-server.de");

		dummyconf.set("hbase.zookeeper.quorum",
				"static.232.40.46.78.clients.your-server.de");

		dummyconf.set("hbase.zookeeper.property.clientPort", "2181");

		dummyconf.set("zookeeper.znode.parent", "/hbase-unsecure");
//	//	dummyconf.set(
//				org.apache.hadoop.hbase.mapreduce.TableInputFormat.INPUT_TABLE,
//				"apiLog");

		
		

	//	dummyconf.addResource(HBaseConfigurationConstansts.HBASE_CONF_FILE);
	//	dummyconf.set("hbase.zookeeper.quorum",HBaseConfigurationConstansts.MASTER_IP);
	//	dummyconf.set("hbase.zookeeper.property.clientPort",HBaseConfigurationConstansts.ZOOKEEPER_PORT);
	//	dummyconf.set("zookeeper.znode.parent", "/hbase-unsecure");
		Configuration conf=  HBaseConfiguration.create(dummyconf);


		HTable table=null;
		try {
			table = new HTable(conf, "apiLog");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	//	HTable table = new HTable(conf, "apiLog");

		// Instantiating Get class
		Get g = new Get(Bytes.toBytes("12528479b0+1484498588584+rF1hz2HG"));

		// Reading the data
		Result result=null;;
		try {
			result = table.get(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Reading values from Result class object
		byte[] value = result.getValue(Bytes.toBytes("f3"),
				Bytes.toBytes("msg"));

		System.out.println("result is="+Bytes.toString(value));
		try {
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void testing(){
		Configuration dummyconf = new Configuration();

		dummyconf.addResource(HBaseConfigurationConstansts.HBASE_CONF_FILE);
		dummyconf.set("hbase.zookeeper.quorum",HBaseConfigurationConstansts.MASTER_IP);
		dummyconf.set("hbase.zookeeper.property.clientPort",HBaseConfigurationConstansts.ZOOKEEPER_PORT);
		dummyconf.set("zookeeper.znode.parent", "/hbase-unsecure");
		Configuration conf=  HBaseConfiguration.create(dummyconf);


		HTable table = new HTable(conf, "apiLog");

		// Instantiating Get class
		Get g = new Get(Bytes.toBytes("12528479b0+1484498588584+rF1hz2HG"));

		// Reading the data
		Result result = table.get(g);

		// Reading values from Result class object
		byte[] value = result.getValue(Bytes.toBytes("f3"),
				Bytes.toBytes("msg"));

		System.out.println("result is="+Bytes.toString(value));
		table.close();

	}
	
}
