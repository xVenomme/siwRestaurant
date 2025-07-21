package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Menu;
import it.uniroma3.siw.service.MenuService;
import it.uniroma3.siw.service.PiattoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private PiattoService piattoService;

    /* ----------------- LISTA DI TUTTI I MENU ----------------- */
    @GetMapping({"", "/", "/lista"})
    public String listaMenu(Model model) {
        model.addAttribute("menus", menuService.findAll());
        return "menu/listaMenu";           // templates/menu/listaMenu.html
    }

  

    /* ------ Salva il menù (crea o aggiorna) – solo ADMIN ----- */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveMenu(@Valid @ModelAttribute("menu") Menu menu,
                           BindingResult br,
                           Model model,
                           RedirectAttributes ra) {

        if (br.hasErrors()) {
            model.addAttribute("piatti", piattoService.findAll());
            return "menu/form";    // ← rimostra sempre form.html
        }

        boolean isNew = (menu.getId() == null);
        menuService.save(menu);
        ra.addFlashAttribute("success",
            isNew ? "Menù creato con successo!" : "Menù aggiornato con successo!");

        return "redirect:/menu/lista";
    }


    /* ----------------------- DETTAGLIO ----------------------- */
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public String dettaglio(@PathVariable Long id, Model model) {
        Menu menu = menuService.getById(id);
        menu.getPiatti().size();          // forza l’inizializzazione
        model.addAttribute("menu", menu);
        return "menu/detail";
    }


    /* ---------------- ELIMINA MENU (ADMIN) ------------------- */
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteMenu(@PathVariable Long id,
                             RedirectAttributes ra) {

        menuService.deleteById(id);
        ra.addFlashAttribute("success", "Menù eliminato con successo!");
        return "redirect:/menu/lista";        // ← via fissa, niente più Referer
    }
    
    /* ---------------- AGGIUNGE MENU (ADMIN) ------------------ */
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String nuovoMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("piatti", piattoService.findAll());
        return "menu/form";        // ← unico template
    }
    /* ---------------- MODIFICA MENU (ADMIN) ------------------ */
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editMenuForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.getById(id);
        if (menu == null) {
            return "redirect:/menu/lista";
        }
        model.addAttribute("menu", menu);
        model.addAttribute("piatti", piattoService.findAll());
        return "menu/form";        // ← stesso template
    }
}
