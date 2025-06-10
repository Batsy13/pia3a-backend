package br.com.project.pi.application.exception;

public class AccessDeniedViewListException extends RuntimeException {

    public AccessDeniedViewListException() {
        super("Você não tem permissão para essa lista.");
    }
}
