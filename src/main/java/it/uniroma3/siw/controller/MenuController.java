package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Menu;
import it.uniroma3.siw.service.MenuService;
import it.uniroma3.siw.service.PiattoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private PiattoService piattoService;

    /* ---------- LISTA DI TUTTI I MENU ------------------- */
    @GetMapping({"", "/", "/lista"})
    public String listaMenu(Model model) {
        model.addAttribute("menus", menuService.findAll());
        return "menu/listaMenu";                   // templates/menu/lista.html
    }

    /* ---------- DETTAGLIO SINGOLO MENU ------------------ */
    @GetMapping("/{id}")
    public String getMenu(@PathVariable Long id, Model model) {
        Menu menu = menuService.getById(id);
        if (menu == null)                      // se non esiste, torna alla lista
            return "redirect:/menu/lista";

        model.addAttribute("menu", menu);
        return "menu/menu";                    // templates/menu/menu.html
    }

    /* ---------- FORM CREAZIONE (ADMIN) ------------------ */
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String nuovoMenuForm(Model model) {
        model.addAttribute("menu",   new Menu());
        model.addAttribute("piatti", piattoService.findAll());
        return "menu/formMenu";                // templates/menu/formMenu.html
    }

    /* ---------- SALVATAGGIO MENU (ADMIN) ---------------- */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String salvaMenu(@Valid @ModelAttribute Menu menu,
                            BindingResult br,
                            Model model) {

        if (br.hasErrors()) {
            model.addAttribute("piatti", piattoService.findAll());
            return "menu/formMenu";
        }

        menuService.save(menu);
        return "redirect:/menu/listaMenu";
    }

    /* ---------- ELIMINA MENU (ADMIN) -------------------- */
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteMenu(@PathVariable Long id,
                             @RequestHeader(value = "Referer", required = false) String ref) {

        menuService.deleteById(id);
        return (ref != null) ? "redirect:" + ref : "redirect:/menu/lista";
    }

    /* ---------- MODIFICA MENU (ADMIN) ------------------- */
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editMenuForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.getById(id);
        if (menu == null)
            return "redirect:/menu/listaMenu";

        model.addAttribute("menu", menu);
        model.addAttribute("piatti", piattoService.findAll());
        return "menu/formMenu";
    }
}
