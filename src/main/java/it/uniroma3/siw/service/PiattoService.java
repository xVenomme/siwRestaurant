package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PiattoRepository;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class PiattoService {

	@Autowired
	PiattoRepository piattorepository;
	 public Piatto findById(Long id) {
	        return this.piattorepository.findById(id).orElse(null);
	    }

   
}
