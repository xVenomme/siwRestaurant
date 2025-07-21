package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Home page + gestione delle recensioni generali del ristorante.
 */
@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;   // fornisce findLatest() e findAll() :contentReference[oaicite:0]{index=0}

    @Autowired
    private UserService userService;

    // --- Nuovo: popola la home con le ultime 5 recensioni ---
    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("latestReviews", reviewService.findLatest(5));
        return "index";   // nome del template di home
    }

    // --- Nuovo: mostra tutte le recensioni in una pagina dedicata ---
    @GetMapping("/reviews")
    public String showAllReviews(Model model) {
        model.addAttribute("reviews", reviewService.findAll());
        return "reviews-list";   // template che creerai sotto
    }

    /* Esistenti: form nuova recensione, salvataggio e cancellazione */
    @GetMapping("/reviews/new")
    @PreAuthorize("isAuthenticated()")
    public String showNewReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "recensioni";    // template del form :contentReference[oaicite:1]{index=1}
    }

    @PostMapping("/reviews")
    @PreAuthorize("isAuthenticated()")
    public String submitReview(@Valid @ModelAttribute("review") Review review,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "recensioni";

        review.setUser(userService.getCurrentUser());
        reviewService.save(review);
        return "redirect:/#recensioni";
    }

    @PostMapping("/reviews/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteReview(@PathVariable Long id,
                               @RequestHeader(value = "Referer", required = false) String referrer) {
        reviewService.deleteById(id);
        return (referrer != null) ? "redirect:" + referrer
                                  : "redirect:/#recensioni";
    }
}

