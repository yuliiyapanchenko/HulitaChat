package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jpanchenko on 10.11.2015.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/addUserForm", method = RequestMethod.GET)
    public String getAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUserForm";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/login";
    }
}
