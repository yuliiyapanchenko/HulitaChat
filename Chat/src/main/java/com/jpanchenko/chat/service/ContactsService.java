package com.jpanchenko.chat.service;

import com.jpanchenko.chat.model.Contact;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.repository.ContactsRepository;
import com.jpanchenko.chat.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jpanchenko on 14.12.2015.
 */
@Service
public class ContactsService {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactsRepository contactsRepository;

    public boolean addContact(int id) {
        User currUser = userService.getUserByUsername(SecurityUtil.getCurrentUsername());
        User contact = userService.getUserById(id);
        if (currUser != null && contact != null) {
            Contact dbContact = new Contact();
            dbContact.setUser(currUser);
            dbContact.setContact(contact);
            return contactsRepository.addContact(dbContact);
        }
        return false;
    }
}
