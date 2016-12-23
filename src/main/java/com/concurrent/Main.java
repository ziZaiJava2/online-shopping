package com.concurrent;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
////		ExecutorService es = Executors.newCachedThreadPool();
//		ExecutorService es = Executors.newFixedThreadPool(5);
////		ExecutorService es = Executors.newSingleThreadExecutor();
//		long start = System.currentTimeMillis();
//		List<Future<Integer>> fs = new ArrayList<Future<Integer>>();
//		for(int i = 0;i < 10 ; i++) {
////			es.submit(Callable<Integer> );
//			Future<Integer>  f = es.submit(new Task());
////			Thread.sleep(1000);
//			fs.add(f);
//		}
//		
//		for(Future<Integer> fss : fs){
//			System.out.println(System.currentTimeMillis()- start + "     " +fss.get());
//		}
//		
////		Thread.sleep(1000);
//		long end = System.currentTimeMillis() - start;
//		logger.info("index of count {}", end);
//		es.shutdown();

		Task t = new Task();
		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);
		Thread t3 = new Thread(t);
		t1.start();
		t2.start();
		t3.start();
//		Thread.sleep(1000);
//		logger.info("count=" + Task.count);

//		Thread thread = new Thread(new Task());
//		thread.start();
//
//		for(int i = 0; i< 100; i++){
//			logger.info("index: ()", i);
//		}
	}
}
