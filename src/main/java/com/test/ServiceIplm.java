package com.test;

public class ServiceIplm implements Service{
    private MySqlDao dao;

	public MySqlDao getDao() {
		return dao;
	}

	public void setDao(MySqlDao dao) {
		this.dao = dao;
	}
	
	public String toString(){
		return "ServiceIplm dao : " + dao;
		
	}
}
