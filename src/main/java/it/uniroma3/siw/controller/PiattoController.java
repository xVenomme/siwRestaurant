package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.service.PiattoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Controller
@RequestMapping("/piatto")
public class PiattoController {

    @Autowired
    private PiattoService piattoService;

    @GetMapping("/formPiatto")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public String showForm(Model model) {
        model.addAttribute("piatto", new Piatto());
        return "piatto/formPiatto";          // <-- cartella inclusa
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public String savePiatto(@Valid @ModelAttribute Piatto piatto,
                             BindingResult br,
                             @RequestParam("imageFile") MultipartFile file) throws IOException {

        if (br.hasErrors())
            return "piatto/formPiatto";

        piattoService.saveWithImage(piatto, file);
        return "redirect:/piatto/tuttiPiatti";   // <-- URL completo
    }
    
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")   // o il tuo ruolo reale
    public String deletePiatto(@PathVariable Long id,
                               @RequestHeader(value = "Referer", required = false) String ref) {

        piattoService.deleteById(id);
        return (ref != null) ? "redirect:" + ref
                             : "redirect:/piatto/tuttiPiatti";
    }


    @GetMapping("/tuttiPiatti")
    public String mostraTuttiPiatti(Model model){
        model.addAttribute("piatti", piattoService.findAll());
        return "piatto/listaPiatto";            // <-- cartella inclusa
    }
    
    
  
    

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public String updatePiatto(@PathVariable Long id,
                               @Valid @ModelAttribute Piatto piatto,
                               BindingResult bindingResult,
                               @RequestParam("imageFile") MultipartFile file) throws IOException {
    	System.out.println("Modifica invocata");
    	System.out.println("Modifica invocata");
        if (bindingResult.hasErrors()) {
            return "piatto/formPiatto";
        }

        piatto.setId(id); // Assicurati che l'ID venga riapplicato
        piattoService.updateWithImage(piatto, file); // Devi implementare questa logica nel service
        return "redirect:/piatto/tuttiPiatti";
    }
    
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Piatto piatto = piattoService.findById(id);
        if (piatto == null) {
            return "redirect:/piatto/tuttiPiatti";
        }

        model.addAttribute("piatto", piatto);
        return "piatto/formPiatto";
    }



    
    
    
}

