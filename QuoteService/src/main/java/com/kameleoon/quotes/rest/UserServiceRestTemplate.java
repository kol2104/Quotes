package com.kameleoon.quotes.rest;

import com.kameleoon.quotes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserServiceRestTemplate {

    @Value("${user.service.host}")
    private String USER_SERVICE_HOST;
    @Value("${user.service.port}")
    private String USER_SERVICE_PORT;
    @Value("${user.service.path}")
    private String USER_SERVICE_PATH;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<User> getUserById(String userId) {
        return Optional.ofNullable(restTemplate.getForObject(
                "http://" + USER_SERVICE_HOST + ":" + USER_SERVICE_PORT + USER_SERVICE_PATH + "/" + userId, User.class));
    }
}
