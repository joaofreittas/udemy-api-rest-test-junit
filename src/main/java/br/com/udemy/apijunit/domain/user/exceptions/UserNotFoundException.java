package br.com.udemy.apijunit.domain.user.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String message) {
        super(message);
    }

}
