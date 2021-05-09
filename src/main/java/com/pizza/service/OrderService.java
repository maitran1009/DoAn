package com.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pizza.common.PageConstant;
import com.pizza.dao.OrderDao;
import com.pizza.model.entity.Order;
import com.pizza.model.output.OrderListOutput;
import com.pizza.repository.OrderDetailRepository;
import com.pizza.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private SendMailService sendMailService;
	
	@Autowired
	private OrderDao orderDao;

	public String getListOrder(Model model) {
		model.addAttribute("orders", orderDao.getListOrder(1, ""));
		return PageConstant.PAGE_ORDER_LIST;
	}
	
	public OrderListOutput orderListAjax(int page, String keyword) {
		return orderDao.getListOrder(page, keyword);
	}

	public void deleteOrder(int id) {
		Order order = orderRepository.findById(id).get();
		orderDetailRepository.deleteAll(order.getOrderDetails());
		orderRepository.delete(order);

		sendMailService.sendMailPaySuccess(order, order.getOrderDetails(), false);
	}
}
