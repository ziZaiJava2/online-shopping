package com.station;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWeiWei {

	public static void main(String[] args) {

		List<String> line1 = new ArrayList<String>();
		List<String> line2 = new ArrayList<String>();
		List<String> line3 = new ArrayList<String>();
		List<String> line4 = new ArrayList<String>();
		
		List<List<String>> lines = new ArrayList<List<String>>();
		lines.add(line1);
		lines.add(line2);
		lines.add(line3);
		lines.add(line4);
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		
		
		es.execute(new PeopleCreater());
	}

	public class TicketSeller implements Runnable {

		private List line;

		public TicketSeller(List<String> line) {
			this.line = line;
		}

		@Override
		public void run() {

			while (true) {

				if (!line.isEmpty()) {
					synchronized (line) {
						line.remove(0);
					}
				}
			}
		}
	}

	public class PeopleCreater implements Runnable {

		private List<List<String>>[] lines;

		public PeopleCreater(List<List<String>>... lines) {
			this.lines = lines;
		}

		@Override
		public void run() {

			while (true) {
				List targetLine = null;
				int minLength = 0;
				for (List line : lines) {
					int curLength = line.size();
					if (curLength < minLength) {
						targetLine = line;
						minLength = curLength;
					}
				}
				synchronized (targetLine) {
					targetLine.add("new-person");
				}
			}
			
		}

	}
}
