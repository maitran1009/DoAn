package com.pizza.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.model.entity.Product;
import com.pizza.model.entity.Size;
import com.pizza.repository.ProductRepository;
import com.pizza.repository.SizeRepository;

@Service
public class HomeService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SizeRepository sizeRepository;

	public List<Product> getListProduct() {
		return productRepository.findAll();
	}

	public List<Size> getListSize() {
		return sizeRepository.findAll();
	}
}
