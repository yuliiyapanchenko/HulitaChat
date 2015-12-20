package com.jpanchenko.chat.repository;

import com.jpanchenko.chat.model.SocialMediaService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Julia on 05.12.2015.
 */
@Repository
public class SocialMediaServiceRepository {

    @PersistenceContext(name = "ChatPersistenceUnit")
    private
    EntityManager entityManager;

    @Transactional
    public SocialMediaService getSocialMediaService(String provider) {
        try {
            return entityManager.createQuery("from SocialMediaService where signInProvider =:provider",
                    SocialMediaService.class)
                    .setParameter("provider", provider)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
}
