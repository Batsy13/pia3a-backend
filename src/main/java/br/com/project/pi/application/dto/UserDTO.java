package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.User;

public class UserDTO {
    private String email;
    private String password;
    public UserDTO(User newUser) {
        this.email = newUser.getUsername();
        this.password = newUser.getPassword();
    }
}
