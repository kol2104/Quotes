package com.kameleoon.view.controller;

import com.kameleoon.view.model.Quote;
import com.kameleoon.view.model.User;
import com.kameleoon.view.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MainService mainService;

    private final String USER_ID_COOKIE = "user";
    private final String USER_STRING_ATTRIBUTE = "user";

    @GetMapping
    public String getMainPage(HttpServletRequest request, Model model) {
        Cookie userCookie = findCookie(request.getCookies(), USER_ID_COOKIE);
        Optional<User> userOptional = Optional.empty();
        if (userCookie != null) {
            userOptional = mainService.getUserById(userCookie.getValue());
        }
        prepareToMainPage(userOptional, model);
        return "main";
    }

    @GetMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, HttpServletResponse response, Model model) {
        Optional<User> userOptional = mainService.getUserByLoginAndPassword(login, password);
        userOptional.ifPresent((user) ->
                response.addCookie(new Cookie(USER_ID_COOKIE, user.getUserId())));
        prepareToMainPage(userOptional, model);
        return "main";
    }

    @GetMapping("/flop")
    public String getFlopPage(HttpServletRequest request, Model model) {
        Cookie userCookie = findCookie(request.getCookies(), USER_ID_COOKIE);
        Optional<User> userOptional = Optional.empty();
        if (userCookie != null) {
            userOptional = mainService.getUserById(userCookie.getValue());
        }
        String userStringToView = mainService.getUserStringToView(userOptional);
        model.addAttribute(USER_STRING_ATTRIBUTE, userStringToView);

        Quote randomQuote = mainService.getRandomQuote();
        model.addAttribute("randomQuote", randomQuote);

        List<Quote> quoteList = mainService.getFlopTenQuotes();
        String quotesStringToView = mainService.getQuotesStringToView(quoteList);
        model.addAttribute("quotes", quotesStringToView);
        return "main";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/create-user")
    public String registration(@RequestParam String login, @RequestParam String email, @RequestParam String password,
                               HttpServletResponse response, Model model) {
        Optional<User> userOptional = mainService.saveUser(login, email, password);
        userOptional.ifPresent((user) ->
                response.addCookie(new Cookie(USER_ID_COOKIE, user.getUserId())));
        prepareToMainPage(userOptional, model);
        return "main";
    }

    @PostMapping("/increment")
    public ResponseEntity increment(HttpServletRequest request, @RequestParam String quoteId) {
        Cookie userCookie = findCookie(request.getCookies(), USER_ID_COOKIE);
        Optional<User> userOptional = Optional.empty();
        if (userCookie != null) {
            userOptional = mainService.getUserById(userCookie.getValue());
        }
        if (userOptional.isPresent()) {
            mainService.increment(quoteId, userOptional.get());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/decrement")
    public ResponseEntity decrement(HttpServletRequest request, @RequestParam String quoteId) {
        Cookie userCookie = findCookie(request.getCookies(), USER_ID_COOKIE);
        Optional<User> userOptional = Optional.empty();
        if (userCookie != null) {
            userOptional = mainService.getUserById(userCookie.getValue());
        }
        if (userOptional.isPresent()) {
            mainService.decrement(quoteId, userOptional.get());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/profile")
    public String getProfilePage(@RequestParam(name = "user") String userId, Model model) {
        Optional<User> user = mainService.getUserById(userId);
        String profileToView = mainService.buildStringProfileToView(user);
        model.addAttribute("profile", profileToView);
        return "profile";
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity handleException(Exception exception) {
        Object errorBody = exception.getMessage();
        return new ResponseEntity(errorBody, HttpStatus.BAD_REQUEST);
    }

    private void prepareToMainPage(Optional<User> userOptional, Model model) {
        String userStringToView = mainService.getUserStringToView(userOptional);
        model.addAttribute(USER_STRING_ATTRIBUTE, userStringToView);

        Quote randomQuote = mainService.getRandomQuote();
        model.addAttribute("randomQuote", randomQuote);

        List<Quote> quoteList = mainService.getTopTenQuotes();
        String quotesStringToView = mainService.getQuotesStringToView(quoteList);
        model.addAttribute("quotes", quotesStringToView);
    }

    private Cookie findCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
