package com.pizza.model.output;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.pizza.model.entity.Product;
import com.pizza.model.entity.ProductDetail;

public class ProductOutput {
	private int id;
	private String name;
	private String image;
	private long price;
	private String description;
	private List<ProductDetailOutput> productDetail;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProductDetailOutput> getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(List<ProductDetailOutput> productDetail) {
		this.productDetail = productDetail;
	}

	public ProductOutput convertTo(Product product) {
		if (!ObjectUtils.isEmpty(product)) {
			List<ProductDetailOutput> detailOutputs = new ArrayList<>();
			this.id = product.getId();
			this.image = product.getImage().replace("/images/", "");
			this.name = product.getName();
			this.price = product.getPrice();
			this.description = product.getDescription();
			if (!ObjectUtils.isEmpty(product.getProductDetails())) {
				ProductDetailOutput output;
				String statusName;
				for (ProductDetail item : product.getProductDetails()) {
					output = new ProductDetailOutput();
					output.setId(item.getId());
					output.setSize(item.getSize().getId());
					output.setSizeName(item.getSize().getName());
					output.setStatus(item.getStatus());
					statusName = "Còn hàng";
					if (item.getStatus() == 0) {
						statusName = "Hết hàng";
					}
					output.setStatusName(statusName);
					detailOutputs.add(output);
				}
			}
			this.productDetail = detailOutputs;
		}
		return this;
	}
}
