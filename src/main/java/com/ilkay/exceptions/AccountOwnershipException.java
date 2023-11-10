package com.ilkay.exceptions;


public class AccountOwnershipException extends RuntimeException {
    public AccountOwnershipException(String message) {
        super(message);
    }
}
