package com.pizza.model.output;

import com.pizza.common.Utils;
import com.pizza.model.entity.ProductDetail;

public class Cart {
	private int id;
	private int count;
	private int productId;
	private int sizeId;
	private ProductDetail productDetail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getSizeId() {
		return sizeId;
	}

	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}

	public String getAmount() {
		int money = (int) (this.productDetail.getProduct().getPrice() * this.count);
		return Utils.currencyMoney(money);
	}
}
