package com.htmsl.rocq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class TermFrequency {

	private static HashMap<String, Long> docFrequent = new HashMap<String, Long>();

	public static void main(String[] args) {
		String logFile = "/Users/gaurav/Desktop/termfrequencytest.txt";
		
		SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("local[2]").set("spark.executor.memory","1g");;
		
		
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> logData = sc.textFile(logFile).cache();
		
		final long docCount = logData.count();
		
		System.out.println("COUNT :" + docCount);

		JavaPairRDD<String, Long> rdd = logData.flatMap(new FlatMapFunction<String, String>() {

			public Iterator<String> call(String t) throws Exception {
				// TODO Auto-generated method stub
				return Arrays.asList(t.split(" ")).iterator();
			}

		}).mapToPair(new PairFunction<String, String, Long>() {

			public Tuple2<String, Long> call(String arg0) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2<String, Long>(arg0, 1L);
			}

		}).reduceByKey(new Function2<Long, Long, Long>() {

			public Long call(Long a, Long b) throws Exception {
				long add = (long) (a + b);
				return add;
			}

		});

		System.out.println("\n\n\n\n\n" + rdd.collect() + "\n\n\n\n\n");

		rdd.foreach(new VoidFunction<Tuple2<String, Long>>() {

			@SuppressWarnings("null")
			public void call(Tuple2<String, Long> t) throws Exception {
				// TODO Auto-generated method stub
				if (t._1 != null && t._2 != 0L) {
					long value = t._2 / docCount;
					docFrequent.put(t._1, value);
				}
			}

		});

		for (Map.Entry<String, Long> entry : docFrequent.entrySet()) {
			String key = entry.getKey();
			if (key != null) {
				Long value = entry.getValue();
				System.out.println(key + " says , i have this : " + value);
			}
		}
	}

}
