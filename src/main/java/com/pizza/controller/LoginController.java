package com.pizza.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizza.service.UserService;

@Controller
@RequestMapping("/dang-nhap")
public class LoginController {
	@Autowired
	private UserService userService;

	@GetMapping
	public String pageLogin(Model model, HttpSession session, HttpServletRequest request) {
		return userService.pageLogin(model, session, request);
	}
}
