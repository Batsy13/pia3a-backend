package br.com.project.pi.application.repositories;

import br.com.project.pi.application.model.Lists;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<Lists, Long> {
}