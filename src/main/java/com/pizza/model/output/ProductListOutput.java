package com.pizza.model.output;

import java.util.List;

public class ProductListOutput {
	private Pagination pagination;
	private List<ProductOutput> products;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<ProductOutput> getProducts() {
		return products;
	}

	public void setProducts(List<ProductOutput> products) {
		this.products = products;
	}
}
