package br.com.project.pi.application.exception;

public class MissingTokenException extends RuntimeException {

    public MissingTokenException() {
        super("Token ausente.");
    }
}
