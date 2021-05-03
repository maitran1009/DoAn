package com.pizza.service;

import org.springframework.stereotype.Service;

import com.pizza.common.PageConstant;

@Service
public class AdminService {
	public String getDataSearch(String keyword, int page) {
		String result;
		if (page == 1) {
			result = PageConstant.PAGE_PRODUCT_LIST;
		} else if (page == 2) {
			result = PageConstant.PAGE_USER_LIST;
		} else {
			result = PageConstant.PAGE_ORDER_LIST;
		}
		
		return result;
	}
}
