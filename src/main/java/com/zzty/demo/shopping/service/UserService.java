package com.zzty.demo.shopping.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.zzty.demo.shopping.dao.UserDAO;
import com.zzty.demo.shopping.dto.UserDTO;

public class UserService {
	@Autowired
	private UserDAO userDao;
	public void addUser(UserDTO user) throws ClassNotFoundException, SQLException{
		userDao.addUser(user);
	}
	public void deleteUser(int userId) throws SQLException, ClassNotFoundException{
		userDao.deleteUserById(userId);
	}
}
