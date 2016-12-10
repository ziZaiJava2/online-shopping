package com.zzty.demo.shopping.dto;

import java.util.List;

public class ProductionDTO {
	private int id;
	private String name;
	private String description;
	private double originalPrice;
	private double price;
	private List<CategoryDTO> categorys;

	
	public List<CategoryDTO> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<CategoryDTO> categorys) {
		this.categorys = categorys;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
