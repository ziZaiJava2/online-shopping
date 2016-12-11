package com.zzty.demo.shopping.main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzty.demo.shopping.dto.ProductionDTO;
import com.zzty.demo.shopping.service.ProductionService;

public class ProductionDAOMain {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"Application.xml"});
		ProductionService productionService=context.getBean("productionService",ProductionService.class);
		ProductionDTO production=new ProductionDTO();
		production.setName("jiarui");
		production.setDescription("hahahah");
		production.setOriginal_price(100);
		production.setPrice(20);
		productionService.addProduction(production);
	}
}
