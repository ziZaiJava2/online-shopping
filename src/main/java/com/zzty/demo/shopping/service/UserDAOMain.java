package com.zzty.demo.shopping.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDAOMain {

	public static void main(String[] args) {
	@SuppressWarnings("resource")
	ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"application.xml"});
	
	@SuppressWarnings("unused")
	UserService service = context.getBean("userService",UserService.class);
	//UserService userService=new UserService();¿ò¼ÜµÄ ×÷ÓÃ
	//Map<String Object> context = new HashMap<String Object>();
	//context put("userService",userService);
	System.out.println("");

	}

}
