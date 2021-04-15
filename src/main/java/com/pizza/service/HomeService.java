package com.pizza.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import com.pizza.common.PageConstant;
import com.pizza.model.entity.Product;
import com.pizza.model.output.ProductOutput;
import com.pizza.repository.ProductRepository;

@Service
public class HomeService {
	@Autowired
	private ProductRepository productRepository;

	public String getPageHome(Model model) {
		List<ProductOutput> response = new ArrayList<>();
		List<Product> products = productRepository.findAll();
		if (!ObjectUtils.isEmpty(products)) {
			for (Product product : products) {
				response.add(new ProductOutput().convertTo(product));
			}
		}
		model.addAttribute("products", response);
		return PageConstant.PAGE_INDEX;
	}
}
