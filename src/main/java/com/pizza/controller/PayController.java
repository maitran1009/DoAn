package com.pizza.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pizza.common.Constant;
import com.pizza.common.PageConstant;
import com.pizza.model.input.MomoInput;
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

	@SuppressWarnings("deprecation")
	@GetMapping("pay-success")
	public String pagePaySuccess(MomoInput input, HttpSession session) {
		if (!StringUtils.isEmpty(input.getPartnerCode())) {
			PayInput payInput = (PayInput) session.getAttribute(Constant.SESSION_PAY_INPUT);
			payService.createPay(session, payInput);
		}
		return PageConstant.PAGE_PAY_SUCCESS;
	}

	@GetMapping("type")
	public String pagePayType(HttpSession session, Model model) {
		return payService.pagePayType(session, model);
	}

	@GetMapping("success")
	@ResponseBody
	public boolean createPay(PayInput pay, HttpSession session) {
		return payService.createPay(session, pay);
	}

	@GetMapping("confirm")
	@ResponseBody
	public void payConfirm(@RequestParam String email, @RequestParam String fullName, HttpSession session) {
		payService.payConfirm(session, email, fullName);
	}

	@GetMapping("vnpay")
	@ResponseBody
	public String payVnpay(HttpServletRequest request, @RequestParam String bankCode, @RequestParam Integer amount) {
		return payService.payByVnPay(request, bankCode, amount);
	}

	@GetMapping("momo/get-url")
	@ResponseBody
	public String payMomo(PayInput input, HttpSession session) {
		return payService.getUrlPayMomo(input, session);
	}
}
