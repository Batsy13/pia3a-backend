package br.com.project.pi.application.exception;

public class ListsNotFoundException extends RuntimeException {

    public ListsNotFoundException() {
        super("Nenhuma lista encontrada.");
    }
}
