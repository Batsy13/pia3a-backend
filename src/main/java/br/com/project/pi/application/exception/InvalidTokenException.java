package br.com.project.pi.application.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Token invalido.");
    }
}


