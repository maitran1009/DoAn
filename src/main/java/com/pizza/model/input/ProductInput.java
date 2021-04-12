package com.pizza.model.input;

import java.util.List;

public class ProductInput {
	private int id;
	private String name;
	private String description;
	private int price;
	private String image;
	private List<ProductDetailInput> productDetail;

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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<ProductDetailInput> getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(List<ProductDetailInput> productDetail) {
		this.productDetail = productDetail;
	}
}
