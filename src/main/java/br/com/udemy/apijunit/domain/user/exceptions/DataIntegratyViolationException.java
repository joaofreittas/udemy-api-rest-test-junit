package br.com.udemy.apijunit.domain.user.exceptions;

public class DataIntegratyViolationException extends RuntimeException {

    public DataIntegratyViolationException(final String message) {
        super(message);
    }

}
