package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Piatto;

public interface PiattoRepository extends JpaRepository<Piatto, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
