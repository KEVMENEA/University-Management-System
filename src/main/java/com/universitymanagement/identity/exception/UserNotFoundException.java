package com.universitymanagement.identity.exception;

public class UserNotFoundException extends IdentityException {
    public UserNotFoundException(String userNotFound) { super("User not found", 404); }
}
