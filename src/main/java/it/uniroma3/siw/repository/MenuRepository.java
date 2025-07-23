package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Menu;
import it.uniroma3.siw.model.Piatto;

import java.util.List;

//MenuRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	
	List<Menu> findByPiattiContaining(Piatto piatto);
}

