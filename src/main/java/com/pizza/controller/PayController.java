package com.pizza.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizza.model.input.RegisterInput;
import com.pizza.service.PayService;

@Controller
@RequestMapping("pay")
public class PayController {
	@Autowired
	private PayService payService;

	@GetMapping
	public String pagePay(HttpSession session, Model model) {
		return payService.pagePay(session, model);
	}

	@PostMapping
	public String pay(HttpSession session, @ModelAttribute("user") RegisterInput input) {
		return payService.pay(session, input);
	}
}
