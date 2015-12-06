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
        return (SocialMediaService) entityManager.createQuery("from SocialMediaService where signInProvider =: provider")
                .setParameter("provider", provider)
                .getSingleResult();
    }
}
