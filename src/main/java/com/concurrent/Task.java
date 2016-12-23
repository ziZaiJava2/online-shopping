package com.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task implements Runnable{
	public static final Logger logger = LoggerFactory.getLogger(Main.class);
	private int ticket = 5;
	@Override
	public void run() {
		for(int i = 0; i < ticket ; i++){
			synchronized(this){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("³µÆ±£º" + ticket--);
				logger.info("count of ticket  {} ", ticket);
			}
		}
	}

}
