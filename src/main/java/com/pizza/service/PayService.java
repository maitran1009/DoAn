package com.pizza.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import com.pizza.common.Utils;
import com.pizza.model.entity.Order;
import com.pizza.model.entity.OrderDetail;
import com.pizza.model.input.RegisterInput;
import com.pizza.model.output.Cart;
import com.pizza.repository.OrderDetailRepository;
import com.pizza.repository.OrderRepository;

@Service
public class PayService {
	private static final String AMOUNT = "amount";
	private static final String SESSION_CART = "carts";
	private static final String REDIRECT_GIO_HANG = "redirect:/gio-hang";

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@SuppressWarnings("unchecked")
	public String pagePay(HttpSession session, Model model) {
		String result = "pay";
		List<Cart> carts = (List<Cart>) session.getAttribute(SESSION_CART);
		if(ObjectUtils.isEmpty(carts)) {
			result = REDIRECT_GIO_HANG;
		}
		model.addAttribute("totalMoney", Utils.currencyMoney((int) Utils.amount(carts)));
		model.addAttribute(AMOUNT, Utils.currencyMoney((int) Utils.amount(carts) + 15000));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackOn = Exception.class)
	public String pay(HttpSession session, RegisterInput input) {
		try {
			List<Cart> carts = (List<Cart>) session.getAttribute(SESSION_CART);
			List<OrderDetail> orderDetails = new ArrayList<>();
			Order order = new Order();
			int amount = 0;

			for (Cart cart : carts) {
				amount += cart.getCount() * cart.getProductDetail().getProduct().getPrice();
			}

			order.setAddress(input.getAddress());
			order.setCreateDate(new Date());
			order.setAmount(amount);
			order.setEmail(input.getEmail());
			order.setFullname(input.getFullname());
			order.setPhone(input.getPhone());
			order.setStatus(1);

			order = orderRepository.save(order);

			for (Cart cart : carts) {
				OrderDetail orderDetail = new OrderDetail();

				orderDetail.setOrder(order);
				orderDetail.setQuantity(cart.getCount());
				orderDetail.setProductDetail(cart.getProductDetail());

				orderDetails.add(orderDetail);
			}

			orderDetailRepository.saveAll(orderDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/trang-chu";
	}
}
