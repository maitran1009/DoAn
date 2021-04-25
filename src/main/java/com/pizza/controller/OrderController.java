package com.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pizza.service.OrderService;

@Controller
@RequestMapping("admin/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("list")
	public String getListOrder(Model model) {
		return orderService.getListOrder(model);
	}

	@GetMapping("delete")
	@ResponseBody
	public void getListOrder(@RequestParam int id) {
		orderService.deleteOrder(id);
	}
}
