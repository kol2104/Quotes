package com.kameleoon.view.rest;

import com.kameleoon.view.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return Optional.ofNullable(restTemplate.exchange("http://" + USER_SERVICE_HOST + ":" + USER_SERVICE_PORT +
                        USER_SERVICE_PATH + "?name=" + login + "&password=" + password,
                HttpMethod.GET, null, User.class).getBody());
    }

    public Optional<User> saveUser(String login, String email, String password) {
        return Optional.ofNullable(restTemplate.exchange("http://" + USER_SERVICE_HOST + ":" + USER_SERVICE_PORT + USER_SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(new User(login, email, password)), User.class).getBody());
    }
}
