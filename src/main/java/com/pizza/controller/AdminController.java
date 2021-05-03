package com.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pizza.service.AdminService;

@Controller
@RequestMapping("/admin" )
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@GetMapping
	public String pageAdmin() {
		return "admin/productlist";
	}
	
	@GetMapping("search")
	public String searchData(@RequestParam String keyword, @RequestParam int page) {
		return adminService.getDataSearch(keyword, page);
	}
}
