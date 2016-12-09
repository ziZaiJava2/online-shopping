package com.zizaitianyuan.spring;

public class ServiceImpl implements Service {

	private Dao dao;
	
	public Dao getDao() {
		return dao;
	}
	
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	@Override
	public String toString() {
		return "service implements, dao:" + dao;
	}
}
