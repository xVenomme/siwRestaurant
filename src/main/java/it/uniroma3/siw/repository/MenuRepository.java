package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Menu;

public interface MenuRepository extends CrudRepository<Menu, Long> {
    
}
