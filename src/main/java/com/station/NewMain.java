package com.station;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewMain {

	private static final Logger logger = LoggerFactory.getLogger(NewMain.class);

	public static synchronized void sellTicket() {
		allTickets--;
		logger.info("count of allTickets {} ", allTickets);
	}

	public static int allTickets = 10;

	public static Passager passager;

	public static final List<Passager> line1 = new ArrayList<Passager>();
	public static final List<Passager> line2 = new ArrayList<Passager>();

	public static boolean temp = true;

	public static void main(String[] args) {

		ExecutorService es = Executors.newCachedThreadPool();

		// 乘客进站。
		es.execute(new Runnable() {
			@Override
			public void run() {
				while (temp) {
					if (allTickets <= 0) {
						temp = false;
					}
						if (line1.size() < line2.size()) {
							passager = new Passager();
							synchronized (line1) {
								line1.add(passager);
							}
							System.out.println(passager.getName() + "加入了line1");
						} else {
							passager = new Passager();
							synchronized (line2) {
								line2.add(passager);
							}
							System.out.println(passager.getName() + "加入了line2");
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}
		});

		// line1 的窗口 卖票
		es.execute(new Runnable() {
			@Override
			public void run() {
				synchronized (this) {
					while (allTickets > 0) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (!line1.isEmpty()) {
							sellTicket();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println(line1.get(0).getName() + "在line1买到了票，然后走了");
							synchronized (line1) {
								line1.remove(0);
							}
						}
					}
				}
			}
		});
		// line2的窗口 卖票
		es.execute(new Runnable() {
			@Override
			public void run() {
				synchronized (this) {
					while (allTickets > 0) {
						if (!line2.isEmpty()) {
							sellTicket();
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.println(line2.get(0).getName() + "在line2买到了票，然后走了");
							synchronized (line2) {
								line2.remove(0);
							}
						}
					}
				}
			}
		});

		es.shutdown();
	}

}
