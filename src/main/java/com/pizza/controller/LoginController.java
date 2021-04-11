package com.pizza.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizza.model.input.LoginInput;
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

	@PostMapping
	public String login(Model model, HttpSession session, HttpServletResponse response,
			@ModelAttribute("user") LoginInput user) {
		return userService.login(model, session, response, user);
	}
}
