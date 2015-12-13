package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Julia on 13.12.2015.
 */
@Controller
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public void addContact() {

    }

    @RequestMapping(path = "/search", method = RequestMethod.POST)
    @ResponseBody
    public List<User> searchContact(@RequestParam String firstName,
                                    @RequestParam String lastName) {
        return userService.search(firstName, lastName);
    }
}
