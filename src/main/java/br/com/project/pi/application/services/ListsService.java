package br.com.project.pi.application.services;

import br.com.project.pi.application.dto.CreatedListRequestDTO;
import br.com.project.pi.application.dto.CreatedPlaceDTO;
import br.com.project.pi.application.dto.ListsDTO;
import br.com.project.pi.application.dto.PlaceDTO;
import br.com.project.pi.application.exception.*;
import br.com.project.pi.application.model.Lists;
import br.com.project.pi.application.model.Place;
import br.com.project.pi.application.model.User;
import br.com.project.pi.application.repositories.ListsRepository;
import br.com.project.pi.application.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListsService {

    @Autowired
    private ListsRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Transactional
    public List<ListsDTO> findAll() {
        var lists = repository.findAll();

        if (lists.isEmpty()) {
            throw new ListsNotFoundException();
        }
        return lists.stream()
                .map(list -> new ListsDTO(
                        list.getId(),
                        list.getName(),
                        list.getIcon(),
                        list.getPlaces().stream()
                                .map(place -> new PlaceDTO(
                                        place.getId(),
                                        place.getName(),
                                        place.getDescription(),
                                        place.getImages(),
                                        place.getAddedAt()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public ListsDTO findById(Long id) {
        Lists list = repository.findById(id)
                .orElseThrow(ListNotFoundException::new);
        return new ListsDTO(list);
    }

    @Transactional
    public CreatedListRequestDTO createdListsPlace(CreatedListRequestDTO dto){
        User user = getAuthenticatedUser();

        if (repository.existsByName(dto.name())) {
            throw new ListNameAlreadyExistsException(dto.name());
        }

        Lists list = new Lists(dto);
        list.setUser(user);

        repository.save(list);
        return new CreatedListRequestDTO(list);
    }

    @Transactional
    public CreatedPlaceDTO createdPlace(CreatedPlaceDTO dto, Long listId) {

        User user = getAuthenticatedUser();

        Lists list = repository.findById(listId)
                .orElseThrow(() -> new ListNotFoundException());

        List<String> imageUrls = Collections.emptyList();
        if (dto.images() != null && !dto.images().isEmpty()) {
            try {
                String baseFileName = imageUploadService.sanitizeFileName (dto.name());
                imageUrls = imageUploadService.uploadImages(baseFileName, dto.images());
            } catch (Exception e) {
                throw new ImageUploadException();
            }
        }

        Place place = new Place();
        place.setName(dto.name());
        place.setDescription(dto.description());
        place.setAddedAt(dto.addedAt());
        place.setImages(imageUrls);
        place.setList(list);

        list.getPlaces().add(place);

        repository.save(list);

        return new CreatedPlaceDTO(
                place.getName(),
                place.getDescription(),
                place.getImages(),
                place.getAddedAt()
        );
    }

    public User getAuthenticatedUser() {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(authentication)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void deleteById(Long id) throws Exception {
        User user = getAuthenticatedUser();
        Lists list = repository.findById(id).orElseThrow(() -> new ListNotFoundException());
        if(!list.getUser().getId().equals(user.getId())){
            throw new AccessDeniedDeleteListException();
        }
        repository.delete(list);
    }
}
