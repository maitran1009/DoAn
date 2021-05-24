package com.pizza.controller;

import com.pizza.model.output.OrderListOutput;
import com.pizza.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    public String getListOrder(Model model) {
        return orderService.getListOrder(model);
    }

    @GetMapping("list-ajax")
    @ResponseBody
    public OrderListOutput orderListAjax(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) String keyword) {
        return orderService.orderListAjax(page, keyword);
    }

    @GetMapping("update")
    @ResponseBody
    public void update(@RequestParam int id, @RequestParam int status) {
        orderService.updateOrder(id, status);
    }

    @GetMapping("delete")
    @ResponseBody
    public void getListOrder(@RequestParam int id) {
        orderService.deleteOrder(id);
    }
}
