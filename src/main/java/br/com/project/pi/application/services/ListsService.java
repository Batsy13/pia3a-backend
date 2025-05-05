package br.com.project.pi.application.services;

import br.com.project.pi.application.dto.ListsDTO;
import br.com.project.pi.application.dto.PlaceDTO;
import br.com.project.pi.application.model.Lists;
import br.com.project.pi.application.repositories.ListsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListsService {

    @Autowired
    private ListsRepository repository;

    @Transactional
    public List<ListsDTO> findAll() {
        return repository.findAll().stream()
                .map(list -> new ListsDTO(
                        list.getId(),
                        list.getName(),
                        list.getIcon(),
                        list.getPlaces().stream()
                                .map(place -> new PlaceDTO(
                                        place.getId(),
                                        place.getName(),
                                        place.getDescription(),
                                        place.getImage(),
                                        place.getAddedAt()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public ListsDTO findById(Long id) {
        Lists list = repository.findById(id).get();
        return new ListsDTO(list);
    }
}
