package com.zzty.demo.shopping.dto;

import java.util.List;

public class CategoryDTO {
	private int id;
	private String name;
	private List<ProductionDTO> productions;

	public List<ProductionDTO> getProductions() {
		return productions;
	}

	public void setProductions(List<ProductionDTO> productions) {
		this.productions = productions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
