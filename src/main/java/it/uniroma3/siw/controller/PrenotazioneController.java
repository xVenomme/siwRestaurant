package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.service.UserService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenService;

    @Autowired
    private UserService userService;

    /* ---------- mostra il form --------------------------- */
    @GetMapping("/prenota")
    public String showForm(Model model) {
        model.addAttribute("prenotazione", new Prenotazione());
        return "prenotazione";          // templates/prenotazione.html
    }

    /* ---------- salva la prenotazione -------------------- */
    @PostMapping("/prenotazioni")
    public String savePrenotazione(@Valid @ModelAttribute Prenotazione prenotazione,
                                   BindingResult br) {

        if (br.hasErrors())
            return "prenotazione";

        // collega l’utente loggato, se esiste il campo User nell’entità
        User current = userService.getCurrentUser();
        prenotazione.setUser(current);

        prenService.save(prenotazione);
        return "redirect:/prenotazioni/mie";   // dopo il salvataggio
    }

    /* ---------- lista / gestione admin ------------------- */
    @GetMapping("/admin/prenotazioni")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String listaPren(Model m) {
        m.addAttribute("prenotazioni", prenService.findAll());
        return "admin/listaPrenotazioni";     // (da creare)
    }

    /* ---------- prenotazioni personali ------------------- */
    @GetMapping("/prenotazioni/mie")
    @PreAuthorize("isAuthenticated()")
    public String miePrenotazioni(Model m) {
        User u = userService.getCurrentUser();
        m.addAttribute("prenotazioni", prenService.findByUser(u));
        return "prenotazioniMie";
    }

    /* ---------- annulla prenotazione (utente) ------------ */
    @PostMapping("/prenotazioni/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String cancella(@PathVariable Long id,
                           @RequestHeader(value = "Referer", required = false) String ref) {

        // opzionale: verifica che la prenotazione appartenga all’utente
        prenService.deleteById(id);
        return (ref != null) ? "redirect:" + ref : "redirect:/prenotazioni/mie";
    }

    /* ---------- annulla prenotazione (admin) ------------- */
    @PostMapping("/admin/prenotazioni/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletePren(@PathVariable Long id,
                             @RequestHeader(value = "Referer", required = false) String ref) {

        prenService.deleteById(id);
        return (ref != null) ? "redirect:" + ref : "redirect:/admin/prenotazioni";
    }
    
    
    
    
    @GetMapping("/admin/listaPrenotazioni")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String listaPrenotazioni(Model model) {
        model.addAttribute("prenotazioni", prenService.findAll());
        return "admin/listaPrenotazioni";
    }


}
