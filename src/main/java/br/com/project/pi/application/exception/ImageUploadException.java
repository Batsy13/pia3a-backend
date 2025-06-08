package br.com.project.pi.application.exception;

public class ImageUploadException extends RuntimeException {
    public ImageUploadException() {
        super("Erro ao fazer upload das imagens: ");
    }
}
