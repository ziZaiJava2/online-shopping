package com.zzty.demo.shopping.main;

import java.sql.SQLException;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzty.demo.shopping.dto.CooperatorDTO;
import com.zzty.demo.shopping.service.CooperatorService;

public class CooperatorDAOMain {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "Application.xml" });
		CooperatorService service = context.getBean("cooperatorService", CooperatorService.class);
		CooperatorDTO cooperator = new CooperatorDTO();
		cooperator.setName("jiarui");
		Date date = new Date();
		cooperator.setRegister_date(date);
		service.addCooperator(cooperator);
		cooperator.setName("aaaa");
		service.addCooperator(cooperator);
		service.deleteCooperator(5);
		service.getCooperatorById(4);
	}

}
