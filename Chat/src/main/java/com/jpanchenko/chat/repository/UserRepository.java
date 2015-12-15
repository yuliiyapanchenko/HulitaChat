package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.dto.UserDto;
import com.jpanchenko.chat.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Created by jpanchenko on 10.11.2015.
 */
@Repository
public class UserRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Transactional
    public User getUserByEmail(String email) {
        try {
            return (User) entityManager.createQuery("from User where email =:email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public Collection<UserDto> search(String firstName, String lastName, String excludeUsername) {
        return entityManager.createQuery(
                "select new com.jpanchenko.chat.dto.UserDto(u.id, u.firstname, u.lastname)" +
                        "from User as u where (u.firstname like :firstName or u.lastname like :lastName) and u.email not like :excludeUsername", UserDto.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .setParameter("excludeUsername", excludeUsername)
                .getResultList();
    }

    @Transactional
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }
}
