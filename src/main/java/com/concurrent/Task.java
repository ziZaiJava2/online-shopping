package com.concurrent;

import java.util.concurrent.Callable;

public class Task implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		Thread.sleep(1000);
		return 10;
	}

//	private static final Logger logger = LoggerFactory.getLogger(Main.class);
//	public static int count = 0;
//	
//	@Override
//	public void run() {
//
//		for(int i = 0; i < 100; i++){
////			logger.info("index of count "+ count);
////			increase();
//			count = count +1;
//
//		}
//	}
	//  Ëø£¡£¡£¡
//	public static synchronized void increase() {
//		count = count +1;
//	}

}
