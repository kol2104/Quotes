package com.kameleoon.quotes.controller;

import com.kameleoon.quotes.exception.EmptyCollectionQuotesException;
import com.kameleoon.quotes.exception.QuoteNotFoundException;
import com.kameleoon.quotes.exception.UserNotFoundException;
import com.kameleoon.quotes.model.Quote;
import com.kameleoon.quotes.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping("/{quoteId}")
    public ResponseEntity<Quote> getQuoteById(@PathVariable("quoteId") String quoteId) {
        Optional<Quote> quoteOptional = quoteService.getQuoteById(quoteId);
        if (quoteOptional.isPresent()) {
            return new ResponseEntity(quoteOptional, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<Quote> saveQuote(@RequestBody Quote quote) {
        return new ResponseEntity(quoteService.saveQuote(quote), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity updateQuote(@RequestBody Quote quote) {
        return new ResponseEntity(quoteService.updateQuote(quote), HttpStatus.OK);
    }

    @DeleteMapping("/{quoteId}")
    public ResponseEntity deleteQuote(@PathVariable("quoteId") String quoteId) {
        quoteService.deleteQuote(quoteId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/increment/{quoteId}")
    public ResponseEntity incrementVote(@PathVariable("quoteId") String quoteId, @RequestParam(name = "user") String userId) {
        if (quoteService.incrementVote(quoteId, userId)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity("User voted already", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/decrement/{quoteId}")
    public ResponseEntity decrementVote(@PathVariable("quoteId") String quoteId, @RequestParam(name = "user") String userId) {
        if (quoteService.decrementVote(quoteId, userId)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity("User voted already", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/random")
    public ResponseEntity<Quote> getRandomQuotes() {
        Quote quote = quoteService.getRandomQuotes();
        if (quote == null) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(quote, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity< List<Quote> > getTopTenQuotes() {
        return new ResponseEntity(quoteService.getTopTenQuotes(), HttpStatus.OK);
    }

    @GetMapping("/flop")
    public ResponseEntity< List<Quote> > getFlopTenQuotes() {
        return new ResponseEntity(quoteService.getFlopTenQuotes(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity< List<Quote> > getAllQuotesByUser(@PathVariable("quoteId") String userId) {
        return new ResponseEntity(quoteService.getAllQuotesByUser(userId), HttpStatus.OK);
    }

    @ExceptionHandler({QuoteNotFoundException.class, EmptyCollectionQuotesException.class, UserNotFoundException.class})
    public ResponseEntity handleException(Exception exception) {
        Object errorBody = exception.getMessage();
        return new ResponseEntity(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity handleExceptionUserService(Exception exception) {
        Object errorBody = exception.getMessage();
        return new ResponseEntity("User not found: " + errorBody, HttpStatus.BAD_REQUEST);
    }
}
