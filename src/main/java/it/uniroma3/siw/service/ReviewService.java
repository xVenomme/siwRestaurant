package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /* -------------------- CRUD base -------------------- */

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    /* -------------------- Utilità ---------------------- */

    /**
     * Ritorna le ultime <code>count</code> recensioni ordinate
     * dal più recente al meno recente.
     * Se non hai un campo {@code createdAt}, l'ordinamento per {@code id}
     * (auto-increment) funziona lo stesso.
     */
    public List<Review> findLatest(int count) {
        PageRequest page = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "id"));
        return reviewRepository.findAll(page).getContent();
    }

    /**
     * Verifica se l'utente ha già scritto una recensione (una sola a testa).
     */
    public boolean hasUserReviewed(User user) {
        return reviewRepository.existsByUser(user);
    }
}
