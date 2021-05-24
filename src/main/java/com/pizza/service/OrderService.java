package com.pizza.service;

import com.pizza.common.PageConstant;
import com.pizza.dao.OrderDao;
import com.pizza.model.entity.Order;
import com.pizza.model.output.OrderListOutput;
import com.pizza.repository.OrderDetailRepository;
import com.pizza.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
        Order order = orderRepository.findById(id).orElse(new Order());
        orderDetailRepository.deleteAll(order.getOrderDetails());
        orderRepository.delete(order);

        sendMailService.sendMailPaySuccess(order, order.getOrderDetails(), false);
    }

    public void updateOrder(int id, int status) {
        Order order = orderRepository.findById(id).orElse(new Order());
        order.setStatus(status);
        orderRepository.save(order);

        sendMailService.sendMailPaySuccess(order, order.getOrderDetails(), false);
    }
}
