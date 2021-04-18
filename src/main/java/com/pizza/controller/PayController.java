package com.pizza.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pizza.model.input.PayInput;
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

	@GetMapping("type")
	public String pagePayType(HttpSession session, Model model) {
		return payService.pagePayType(session, model);
	}

	@PostMapping
	public String pay(HttpServletRequest request, HttpSession session, @RequestBody PayInput input) {
		return payService.pay(request, session, input);
	}

	@GetMapping("vnpay")
	@ResponseBody
	public String payVnpay(HttpServletRequest request, @RequestParam String bankCode, @RequestParam Integer amount) {
		return payService.payByVnPay(request, bankCode, amount);
	}
}
