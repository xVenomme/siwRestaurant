package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Menu;
import it.uniroma3.siw.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    /* ---------------------- CRUD base ---------------------- */

    public List<Menu> findAll() {
        return menuRepository.findAll();     // JpaRepository restituisce già List<Menu>
    }

    public Menu getById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);    // gestisce sia insert che update
    }

    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }

    /* ---------------------- utilità ------------------------ */

    /**
     * Aggiorna (solo) nome e descrizione di un menù esistente.
     * Restituisce null se l'id non è presente.
     */
    public Menu updateBasic(Long id, String nome, String descrizione) {
        Optional<Menu> opt = menuRepository.findById(id);
        if (opt.isEmpty()) return null;

        Menu m = opt.get();
        m.setNome(nome);
        m.setDescrizione(descrizione);
        return menuRepository.save(m);
    }
}
