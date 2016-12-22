package com.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws InterruptedException {

//		ExecutorService es = Executors.newCachedThreadPool();
		ExecutorService es = Executors.newFixedThreadPool(2);
//		ExecutorService es = Executors.newSingleThreadExecutor();
		long start = System.currentTimeMillis();
		
		for(int i = 0;i < 5 ; i++) {
//			es.submit(Callable<Integer> );
		}
		
		
		long end = System.currentTimeMillis() - start;

		
//		Thread.sleep(1000);
//		logger.info("count=" + Task.count);
		es.shutdown();

//		Thread thread = new Thread(new Task());
//		thread.start();
////		
//		for(int i = 0; i< 100; i++){
//			logger.info("index: ()", i);
//		}
	}
}
