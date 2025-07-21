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
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

 

    /* ---------------------- FORM NUOVA RECENSIONE ---------------------- */
    @GetMapping("/reviews/new")
    @PreAuthorize("isAuthenticated()")
    public String showNewReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "recensioni";          // template del form
    }

    /* ---------------------- SALVATAGGIO RECENSIONE -------------------- */
    @PostMapping("/reviews")
    @PreAuthorize("isAuthenticated()")
    public String submitReview(@Valid @ModelAttribute("review") Review review,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "recensioni";

        User currentUser = userService.getCurrentUser();
        review.setUser(currentUser);
        reviewService.save(review);

        return "redirect:/#recensioni";   // torna alla home con ancora ‘latestReviews’
    }

    /* ---------------------- CANCELLAZIONE (ADMIN) --------------------- */
    @PostMapping("/reviews/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteReview(@PathVariable Long id,
                               @RequestHeader(value = "Referer", required = false) String referrer) {

        reviewService.deleteById(id);
        return (referrer != null) ? "redirect:" + referrer
                                  : "redirect:/#recensioni";
    }
}
