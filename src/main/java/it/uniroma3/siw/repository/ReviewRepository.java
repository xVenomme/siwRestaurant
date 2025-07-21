package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findByBook(Piatto restaurant);
    
    boolean existsByUserAndBook(User user, Piatto restaurant);

}

