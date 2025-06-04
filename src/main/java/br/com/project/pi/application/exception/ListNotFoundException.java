package br.com.project.pi.application.exception;

public class ListNotFoundException extends RuntimeException {

    public ListNotFoundException() {
        super("Lista não encontrada.");
    }
}
