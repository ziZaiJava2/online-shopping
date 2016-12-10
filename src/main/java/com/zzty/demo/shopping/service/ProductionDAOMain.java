package com.zzty.demo.shopping.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProductionDAOMain {

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"application.xml"});
		ProductionService service = context.getBean("productionService",ProductionService.class);
	    System.out.println("");
	}

}
