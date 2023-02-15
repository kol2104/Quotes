package com.kameleoon.quotes.exception;

public class EmptyCollectionQuotesException extends RuntimeException{
    public EmptyCollectionQuotesException() {
        super("Collection quotes is empty");
    }
}
