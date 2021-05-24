package com.pizza.controller;

import com.pizza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dang-nhap")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String pageLogin(Model model) {
        return userService.pageLogin(model);
    }
}
