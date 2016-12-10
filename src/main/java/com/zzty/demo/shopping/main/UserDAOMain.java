package com.zzty.demo.shopping.main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzty.demo.shopping.dto.UserDTO;
import com.zzty.demo.shopping.service.UserService;

public class UserDAOMain {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{

		ApplicationContext context =
				new ClassPathXmlApplicationContext(new String[] {"application.xml"});
		
		//Map<String,Object> context = new HashMap<String, Object>();
		//UserService userService = new UserService();
		//context.put("userService",userService);
		

		UserService service = context.getBean("userService", UserService.class);
		UserDTO user = new UserDTO();
		user.setLoginName("xujialiang");
		service.AddUser(user);
		System.out.println(service);		
	}
}
	