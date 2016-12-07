package com.zzty.demo.shopping.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zzty.demo.shopping.dao.UserDAO;
import com.zzty.demo.shopping.dto.UserAddressDTO;
import com.zzty.demo.shopping.dto.UserDTO;

public class UserDAOMain {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		String[] addresses = {"east nanjing road No.1", "huanhai road No.5", "zhengfu road No.20", "west nanjing road No.1", "xin tian di"};
		List<UserAddressDTO> addressList = new ArrayList<UserAddressDTO>();
		for(String address: addresses){
			UserAddressDTO userAddress= new UserAddressDTO();
			userAddress.setAddress(address);
			if("zhengfu road No.20".equals(address)){
				userAddress.setDefault(true);
			}
			addressList.add(userAddress);
		}
		
		UserDTO user = new UserDTO();
		user.setEmail("weiwei@qq.com");
		user.setLoginName("weiwei");
		user.setNickName("wei");
		user.setAddresses(addressList);
		
		UserDAO userDAO = new UserDAO();
		userDAO.addUser(user);
	}
}
