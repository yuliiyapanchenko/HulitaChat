package com.jpanchenko.chat.service;

import com.jpanchenko.chat.repository.SocialMediaServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Julia on 05.12.2015.
 */
@Service
public class SocialMediaService {

    @Autowired
    SocialMediaServiceRepository socialMediaServiceRepository;


    public com.jpanchenko.chat.model.SocialMediaService getSocialMediaService(String provider) {
        return socialMediaServiceRepository.getSocialMediaService(provider);
    }
}
