package com.zizaitianyuan.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context =
			    new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});

		Service service = context.getBean("service", Service.class);
		Dao dao = context.getBean("dao", Dao.class);
		
		System.out.println(service);
		System.out.println(dao);
	}

}
