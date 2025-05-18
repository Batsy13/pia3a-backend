package br.com.project.pi.application.exception;

public class PasswordIncorrectException extends RuntimeException {

    public PasswordIncorrectException() {
        super("Senha incorreta.");
    }
}
