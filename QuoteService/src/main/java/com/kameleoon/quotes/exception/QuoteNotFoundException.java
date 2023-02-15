package com.kameleoon.quotes.exception;

public class QuoteNotFoundException extends RuntimeException{
    public QuoteNotFoundException(String quoteId) {
        super("Quote id not exist: " + quoteId);
    }
}
