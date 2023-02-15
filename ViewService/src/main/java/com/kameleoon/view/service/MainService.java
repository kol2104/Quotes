package com.kameleoon.view.service;

import com.kameleoon.view.model.Quote;
import com.kameleoon.view.model.User;
import com.kameleoon.view.rest.QuoteServiceRestTemplate;
import com.kameleoon.view.rest.UserServiceRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MainService {

    @Autowired
    private QuoteServiceRestTemplate quoteServiceRestTemplate;
    @Autowired
    private UserServiceRestTemplate userServiceRestTemplate;

    public String getUserStringToView(Optional<User> userOptional) {
        if (userOptional.isPresent()) {
            return buildUserString(userOptional.get());
        }
        return buildFormUnknownUser();
    }

    private String buildUserString(User user) {
        StringBuilder result = new StringBuilder()
                .append("<div class=\"element\">Hello, ")
                .append(user.getName())
                .append("!</div><div class=\"element\"><a href=\"/profile?user=")
                .append(user.getUserId())
                .append("\">Profile</a></div>");
        return result.toString();
    }

    private String buildFormUnknownUser() {
        StringBuilder result = new StringBuilder()
                .append("<form action=\"/login\" method=\"get\">")
                .append("<div class=\"element\"><input type=\"text\" name=\"login\" placeholder=\"Login\" /></div>")
                .append("<div class=\"element\"><input type=\"password\" name=\"password\" placeholder=\"Password\" /></div>")
                .append("<div class=\"element\"><input type=\"submit\" value=\"Login\"/></div>")
                .append("<div class=\"element\"><a href=\"/registration\">Create an account!</a></div>")
                .append("</form>");
        return result.toString();
    }

    public String getQuotesStringToView(List<Quote> quoteList) {
        StringBuilder result = new StringBuilder();
        int i = 1;
        for (Quote quote : quoteList) {
            result.append("<div class=\"quote\">Top ")
                    .append(i++)
                    .append("<div>")
                    .append(quote.getContent())
                    .append("</div><div>Score: ")
                    .append(getScore(quote))
                    .append("</div><div><button onclick=\"incrementVote('")
                    .append(quote.getQuoteId())
                    .append("')\" class=\"vote\" >+</button>")
                    .append("<button onclick=\"decrementVote('")
                    .append(quote.getQuoteId())
                    .append("')\" class=\"vote\" >-</button></div><div>Posted by <a href='/profile?user=")
                    .append(quote.getUserId())
                    .append("'>")
                    .append(getUserById(quote.getUserId())
                            .orElse(new User()).getName())
                    .append("</a></div></div>");
        }
        return result.toString();
    }

    public Quote getRandomQuote() {
        return quoteServiceRestTemplate.getRandomQuote();
    }

    private Long getScore (Quote quote) {
        return quote.getVotes().stream().max((v1, v2) -> v1.getNumberVote().compareTo(v2.getNumberVote())).get().getValueVote();
    }

    public List<Quote> getTopTenQuotes() {
        return quoteServiceRestTemplate.getTopTenQuotes();
    }

    public List<Quote> getFlopTenQuotes() {
        return quoteServiceRestTemplate.getFlopTenQuotes();
    }

    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return userServiceRestTemplate.getUserByLoginAndPassword(login, password);
    }

    public Optional<User> getUserById(String userId) {
        return userServiceRestTemplate.getUserById(userId);
    }

    public Optional<User> saveUser(String login, String email, String password) {
        return userServiceRestTemplate.saveUser(login, email, password);
    }

    public void increment(String quoteId, User user) {
        quoteServiceRestTemplate.increment(quoteId, user);
    }

    public void decrement(String quoteId, User user) {
        quoteServiceRestTemplate.decrement(quoteId, user);
    }

    public String buildStringProfileToView(Optional<User> userOptional) {
        StringBuilder result = new StringBuilder();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            result.append("<div>Login: ")
                    .append(user.getName())
                    .append("</div><div>Email: ")
                    .append(user.getEmail())
                    .append("</div><div>Date of registration: ")
                    .append(user.getDateRegistration())
                    .append("</div>");
        } else {
            result.append("Wrong user id!");
        }
        return result.toString();
    }
}
