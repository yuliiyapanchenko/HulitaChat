package com.jpanchenko.chat.service;

import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.model.Contact;
import com.jpanchenko.chat.model.User;
import com.jpanchenko.chat.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<UserDto> getUserContacts() {
        User user = userService.getLoggedInUser();
        return contactsRepository.getUserContacts(user);
    }

    public List<UserDto> searchContacts(String firstName, String lastName) {
        User currUser = userService.getLoggedInUser();
        List<UserDto> users = new ArrayList<>();
        users.addAll(userService.getUsers(firstName, lastName));
        users.remove(new UserDto(currUser.getId(), currUser.getFirstname(), currUser.getLastname()));
        users.removeAll(contactsRepository.getUserContacts(currUser));
        return users;
    }
}
