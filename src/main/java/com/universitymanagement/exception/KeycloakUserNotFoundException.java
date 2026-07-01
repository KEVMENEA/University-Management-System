package com.universitymanagement.exception;

public class KeycloakUserNotFoundException extends RuntimeException {
    public KeycloakUserNotFoundException(String message) { super(message); }
}