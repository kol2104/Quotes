package com.kameleoon.view.rest;

import com.kameleoon.view.model.Quote;
import com.kameleoon.view.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class QuoteServiceRestTemplate {

    @Value("${quote.service.host}")
    private String QUOTE_SERVICE_HOST;
    @Value("${quote.service.port}")
    private String QUOTE_SERVICE_PORT;
    @Value("${quote.service.path}")
    private String QUOTE_SERVICE_PATH;

    @Autowired
    private RestTemplate restTemplate;

    public Quote getRandomQuote() {
        return restTemplate.getForObject(
                "http://" + QUOTE_SERVICE_HOST + ":" + QUOTE_SERVICE_PORT + QUOTE_SERVICE_PATH + "/random", Quote.class);
    }

    public List<Quote> getTopTenQuotes() {
        return restTemplate.exchange("http://" + QUOTE_SERVICE_HOST + ":" + QUOTE_SERVICE_PORT + QUOTE_SERVICE_PATH + "/top",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Quote>>() {}).getBody();
    }

    public List<Quote> getFlopTenQuotes() {
        return restTemplate.exchange("http://" + QUOTE_SERVICE_HOST + ":" + QUOTE_SERVICE_PORT + QUOTE_SERVICE_PATH + "/flop",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Quote>>() {}).getBody();
    }

    public void increment(String quoteId, User user) {
        restTemplate.postForObject("http://" + QUOTE_SERVICE_HOST + ":" + QUOTE_SERVICE_PORT +
                QUOTE_SERVICE_PATH + "/increment/" + quoteId + "?user=" + user.getUserId(), null, Object.class);
    }

    public void decrement(String quoteId, User user) {
        restTemplate.postForObject("http://" + QUOTE_SERVICE_HOST + ":" + QUOTE_SERVICE_PORT +
                QUOTE_SERVICE_PATH + "/decrement/" + quoteId + "?user=" + user.getUserId(), null, Object.class);
    }
}
