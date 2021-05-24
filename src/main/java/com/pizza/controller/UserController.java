package com.pizza.controller;

import com.pizza.model.entity.User;
import com.pizza.model.input.RegisterInput;
import com.pizza.model.output.UserListOutput;
import com.pizza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("list")
    public String getUserList(Model model) {
        return userService.getUserList(model);
    }

    @GetMapping("list-ajax")
    @ResponseBody
    public UserListOutput userListAjax(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) String keyword) {
        return userService.userListAjax(page, keyword);
    }

    @PostMapping(path = "create")
    @ResponseBody
    public String createUser(@RequestBody RegisterInput user) {
        return userService.register(user);
    }

    @GetMapping("info")
    @ResponseBody
    public User getUserInfo(@RequestParam int id) {
        return userService.getUserInfo(id);
    }

    @GetMapping(path = "delete")
    @ResponseBody
    public void deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
    }
}
