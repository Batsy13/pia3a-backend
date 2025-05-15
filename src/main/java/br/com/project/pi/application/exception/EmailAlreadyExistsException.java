package br.com.project.pi.application.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("Este e-mail já está cadastrado.");
    }
}
