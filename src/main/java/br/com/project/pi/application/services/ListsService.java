package br.com.project.pi.application.services;

import br.com.project.pi.application.dto.ListsDTO;
import br.com.project.pi.application.repositories.ListsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListsService {

    @Autowired
    private ListsRepository repository;

    public List<ListsDTO> findAll() {
        return repository.findAll().stream()
                .map(ListsDTO::new).collect(Collectors.toList());
    }
}
