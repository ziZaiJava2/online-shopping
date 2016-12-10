package com.zzty.demo.shopping.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDAOMain {

	public static void main(String[] args) {
	
	ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"application.xml"});
	
	
	UserService service = context.getBean("userService",UserService.class);
	//UserService userService=new UserService();¿ò¼ÜµÄ ×÷ÓÃ
	//Map<String Object> context = new HashMap<String Object>();
	//context put("userService",userService);
	System.out.println("");

	}

}
