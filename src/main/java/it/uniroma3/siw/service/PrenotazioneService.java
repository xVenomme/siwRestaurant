package it.uniroma3.siw.service;


import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository repo;
    
    

    /* CRUD di base */
    public Prenotazione save(Prenotazione p)          { return repo.save(p); }
    public void deleteById(Long id)                   { repo.deleteById(id); }
    public Prenotazione findById(Long id)             { return repo.findById(id).orElse(null); }
    public List<Prenotazione> findAll()               { return repo.findAll(); }

    public List<Prenotazione> findByUser(User u) {
        return repo.findByUser(u);
    }

    /* (facoltativo) ultime n prenotazioni */
    // public List<Prenotazione> findLatest(int n) { ... }
}
