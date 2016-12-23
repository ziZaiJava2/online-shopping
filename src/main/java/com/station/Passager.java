package com.station;

import java.util.Random;

public class Passager {

	private String name ;

	public Passager(){
		randomSet();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void randomSet(){
		Random random = new Random();
		setName(allName[random.nextInt(allName.length)]);
	}
	
	private String[] allName = {"Ada","Lily",
			"Kai","male",
			"Leon","ooool",
			"Donggua","Pang"};
}



