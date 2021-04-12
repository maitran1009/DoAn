package com.pizza.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizza.common.PageConstant;
import com.pizza.common.SendMail;
import com.pizza.service.HomeService;
import com.pizza.service.UserService;

@Controller
@RequestMapping({ "/", "/trang-chu" })
public class HomeController {
	@Autowired
	private UserService userService;
	
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

	@GetMapping("logout")
	public String logout(Model model, HttpSession session, HttpServletResponse response) {
		return userService.pageLogout(model, session, response);
	}
}
