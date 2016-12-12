package com.zzty.demo.shopping.dto;

public class ProductionDTO {
	private int id;
	private String name;
	private String description;
	private double original_price;
	private double price;
	
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

	public double getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
