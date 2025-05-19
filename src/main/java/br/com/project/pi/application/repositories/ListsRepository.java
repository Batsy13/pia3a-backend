package br.com.project.pi.application.repositories;

import br.com.project.pi.application.model.Lists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListsRepository extends JpaRepository<Lists, Long> {

    List<Lists> findAll();

    @Override
    void deleteById(Long id);
}