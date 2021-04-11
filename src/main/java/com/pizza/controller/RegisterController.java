package com.pizza.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizza.model.input.RegisterInput;
import com.pizza.service.UserService;

@Controller
@RequestMapping("/dang-ky")
public class RegisterController {
	@Autowired
	private UserService userService;

	@GetMapping
	public String pageRegister(Model model, HttpSession session, HttpServletRequest request) {
		return userService.pageRegister(model, session, request);
	}

	@PostMapping
	public String register(Model model, @ModelAttribute("user") RegisterInput user) {
		return userService.register(model, user);
	}
}
