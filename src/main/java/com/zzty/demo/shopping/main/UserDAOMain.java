package com.zzty.demo.shopping.main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzty.demo.shopping.dao.UserDAO;
import com.zzty.demo.shopping.service.UserService;

public class UserDAOMain {
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		ApplicationContext context = //定义一个容器，需要一个配置文件，来决定要提前初始化哪些类，与哪些名字绑定
				new ClassPathXmlApplicationContext(new String[]{"application.xml"});
		//Map<String,Object> context = new HashMap<String,Object>();
		//UserService userService = new UserService();
		//context.put("userServie",userService);
		
		
		
		UserService service = context.getBean("userService",UserService.class);//通过名字早对象，强行转换类型（从object变为xxx）
		UserDAO dao = context.getBean("userDAO",UserDAO.class);
		System.out.println("service");
		
	}
}
