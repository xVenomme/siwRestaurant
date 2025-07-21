package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.*;
import it.uniroma3.siw.service.*;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PiattoService restaurantService;

    @Autowired
    private UserService userService;

    // Mostra form per aggiungere recensione
    @GetMapping("/restaurant/{id}/review")
    public String showReviewForm(@PathVariable("id") Long restaurantId, Model model) {
        Piatto restaurant = restaurantService.findById(restaurantId);
        User user = userService.getCurrentUser();

        if (restaurant == null || user == null) {
            return "redirect:/restaurant/" + restaurantId;
        }

        model.addAttribute("review", new Review());
        model.addAttribute("restaurant", restaurant);
        return "review/formReview";
    }
    
    @PostMapping("/admin/reviews/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")   // oppure hasRole("ADMIN") se usi ROLE_
    public String deleteReview(@PathVariable Long id,
                               @RequestParam Long restaurantId) {
        reviewService.deleteById(id);   // implementa nel service
        return "redirect:/restaurant/" + restaurantId;
    }

    

    // Salva recensione
    @PostMapping("/restaurant/{id}/review")
    public String submitReview(@PathVariable("id") Long restaurantId, 
                               @Valid @ModelAttribute("review") Review review,
                               BindingResult bindingResult,
                               Model model) {

        Piatto restaurant = restaurantService.findById(restaurantId);
        User currentUser = userService.getCurrentUser();

        if (restaurant == null || currentUser == null) {
            return "redirect:/restaurantList";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("restaurant", restaurant);
            return "review/formReview";
        }

        review.setRestaurant(restaurant);
        review.setUser(currentUser);
        review.setId(null); // forza Hibernate a salvarne una nuova
        reviewService.save(review);
        return "redirect:/restaurant/" + restaurantId;
    }
}
