package com.zzty.demo.shopping.main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzty.demo.shopping.dto.UserDTO;
import com.zzty.demo.shopping.service.UserService;

public class UserDAOMain {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "Application.xml" });
		UserService userService = context.getBean("userService", UserService.class);
		userService.addUser(new UserDTO());
		userService.deleteUser(4);
	}
}
