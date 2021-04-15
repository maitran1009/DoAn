package com.pizza.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizza.common.PageConstant;
import com.pizza.common.SendMail;
import com.pizza.service.HomeService;

@Controller
@RequestMapping({ "/", "/trang-chu" })
public class HomeController {
	@Autowired
	private HomeService homeService;

	@Autowired
	private SendMail sendMail;
	
	@GetMapping
	public String pageIndex(Model model) {
		model.addAttribute("products", homeService.getListProduct());
		model.addAttribute("sizes", homeService.getListSize());
		sendMail.sendSimpleEmail();
		return PageConstant.PAGE_INDEX;
	}
	
	@GetMapping("/403")
	public String accessDenied(Principal principal) {
		System.out.println(principal.getName());
		return PageConstant.PAGE_403;
	}
}
