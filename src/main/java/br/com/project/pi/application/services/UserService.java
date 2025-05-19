package br.com.project.pi.application.services;

import br.com.project.pi.application.model.User;
import br.com.project.pi.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Optional<User> findByEmail(String email){
        return repository.findByEmail(email);
    }

}
