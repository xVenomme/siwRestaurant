package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	PiattoRepository piattoRepository;
	 public Piatto findById(Long id) {
	        return this.piattoRepository.findById(id).orElse(null);
	}

    public Piatto save(Piatto piatto) {
        return piattoRepository.save(piatto);
    }

    public void deleteById(Long id) {
        piattoRepository.deleteById(id);
    }

    public List<Piatto> findAll() {
        return (List<Piatto>) piattoRepository.findAll();
    }
   
}
