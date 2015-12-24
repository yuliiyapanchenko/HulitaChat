package com.jpanchenko.chat.controller;

import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Julia on 13.12.2015.
 */
@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addContact(@RequestParam int contactId) {
        contactsService.addContact(contactId);
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public List<UserDto> searchContact(@RequestParam String firstName,
                                       @RequestParam String lastName) {
        return contactsService.searchContacts(firstName, lastName);
    }

    @RequestMapping(path = "/getContacts", method = RequestMethod.GET)
    public List<UserDto> getCurrentUserContacts() {
        return contactsService.getUserContacts();
    }
}
