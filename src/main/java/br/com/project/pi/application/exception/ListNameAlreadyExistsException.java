package br.com.project.pi.application.exception;

public class ListNameAlreadyExistsException extends RuntimeException {
    public ListNameAlreadyExistsException(String message) {
        super("Já existe uma lista com esse nome.");
    }
}
