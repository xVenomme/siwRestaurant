package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Menu;
import it.uniroma3.siw.model.MenuTipo;
import it.uniroma3.siw.repository.MenuRepository;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;
    public Menu getByTipo(MenuTipo tipo){
        return menuRepository.findByTipo(tipo);
    }
}
