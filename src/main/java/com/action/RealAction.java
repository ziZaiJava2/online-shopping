package com.action;

import java.sql.SQLException;
import java.util.List;

import com.zzty.demo.shopping.dao.UserDAO;
import com.zzty.demo.shopping.dto.UserDTO;

public class RealAction {

	public static void main(String[] args) throws SQLException {

		UserDAO user = new UserDAO();

		List<UserDTO> users = user.selectUse();

		for (UserDTO userDTO : users) {
			System.out.println(userDTO.getLogin_name() + "," + userDTO.getNick_name() + "," + userDTO.getEmail() + ","
					+ userDTO.getPassword());
		}
	}

}
