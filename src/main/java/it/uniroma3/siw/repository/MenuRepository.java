package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Menu;
import it.uniroma3.siw.model.MenuTipo;

public interface MenuRepository extends CrudRepository<Menu, Long> {
    Menu findByTipo(MenuTipo tipo);
}
