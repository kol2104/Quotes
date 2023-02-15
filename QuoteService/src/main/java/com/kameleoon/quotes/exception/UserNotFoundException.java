package com.kameleoon.quotes.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String str) {
        super(str);
    }
}