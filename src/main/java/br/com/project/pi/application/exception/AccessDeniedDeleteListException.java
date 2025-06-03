package br.com.project.pi.application.exception;

public class AccessDeniedDeleteListException extends RuntimeException {

    public AccessDeniedDeleteListException() {
        super("Você não tem permissão para excluir essa lista.");
    }
}
