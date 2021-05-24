package com.pizza.controller;

import com.pizza.model.input.RegisterInput;
import com.pizza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dang-ky")
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String pageRegister(Model model) {
        return userService.pageRegister(model);
    }

    @PostMapping
    @ResponseBody
    public String register(@RequestBody RegisterInput user) {
        return userService.register(user);
    }
}
