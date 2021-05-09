package com.pizza.model.output;

import java.util.List;

public class OrderListOutput {
	private Pagination pagination;
	private List<OrderOutput> orders;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<OrderOutput> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderOutput> orders) {
		this.orders = orders;
	}
}
