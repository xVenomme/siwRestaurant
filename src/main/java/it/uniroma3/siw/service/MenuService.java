package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Menu;
import it.uniroma3.siw.repository.MenuRepository;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;
    public Menu getById(Long id){
        return menuRepository.findById(id).get();
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }

    public List<Menu> findAll() {
        return (List<Menu>) menuRepository.findAll();
    }
}
