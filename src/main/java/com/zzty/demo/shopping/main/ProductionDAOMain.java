package com.zzty.demo.shopping.main;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzty.demo.shopping.dao.ProductionDAO;
import com.zzty.demo.shopping.dto.ProductionDTO;
import com.zzty.demo.shopping.service.ProductionService;

public class ProductionDAOMain {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		ProductionDTO production = new ProductionDTO();
		production.setName("book");
		production.setOriginal_price(100);
		production.setPrice(70);
		production.setDescription("good");
		ApplicationContext context =
				new ClassPathXmlApplicationContext(new String[]{"application.xml"});
		ProductionService service = context.getBean("productionService", ProductionService.class);
		ProductionDAO dao = context.getBean("productionDAO", ProductionDAO.class);
		System.out.println("service");
		ProductionDTO pro =service.getProduction(1);
		System.out.println(pro.getName());
		System.out.println(pro.getDescription());
	}

}
