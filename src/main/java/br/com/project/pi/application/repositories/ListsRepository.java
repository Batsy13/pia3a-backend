package br.com.project.pi.application.repositories;

import br.com.project.pi.application.model.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListsRepository extends JpaRepository<Lists, Long> {



    @Override
    void deleteById(Long id);

    boolean existsByName(String name);

    @Query("SELECT l FROM Lists l LEFT JOIN FETCH l.places WHERE l.user.id = :userId")
    List<Lists> findAllByUserIdWithPlaces(Long userId);

}