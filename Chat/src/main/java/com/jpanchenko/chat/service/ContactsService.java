package com.jpanchenko.chat.service;

import com.jpanchenko.chat.dto.UserSearch;
import com.jpanchenko.chat.model.Contact;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jpanchenko on 14.12.2015.
 */
@Service
public class ContactsService {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactsRepository contactsRepository;

    public void addContact(int contactId) {
        User currUser = userService.getLoggedInUser();
        User contact = userService.getUserById(contactId);
        if (currUser != null && contact != null) {
            Contact dbContact = new Contact();
            dbContact.setUser(currUser);
            dbContact.setContact(contact);
            contactsRepository.addContact(dbContact);
        }
    }

    public List<UserSearch> getLoggedInUserContacts() {
        return contactsRepository.getUserContacts(userService.getLoggedInUser());
    }
}
