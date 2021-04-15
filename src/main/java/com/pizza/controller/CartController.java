package com.pizza.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pizza.service.CartService;

@Controller
@RequestMapping("gio-hang")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping
	public String pageCart(Model model, HttpSession session) {
		return cartService.pageCart(model, session);
	}

	@GetMapping("add")
	public String addToCart(Model model, HttpSession session, @RequestParam("id") int id,
			@RequestParam("detail") int detail) {
		return cartService.addToCart(model, session, id, detail);
	}

	@GetMapping("update")
	public String updateToCart(Model model, HttpSession session, @RequestParam("id") int id,
			@RequestParam("flag") int flag) {
		return cartService.updateToCart(model, session, id, flag);
	}

	@GetMapping("delete")
	public String deleteToCart(Model model, HttpSession session, @RequestParam("id") int id) {
		return cartService.deleteToCart(model, session, id);
	}
}
