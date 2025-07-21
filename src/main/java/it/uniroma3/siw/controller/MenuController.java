package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Menu;

import it.uniroma3.siw.service.MenuService;
import it.uniroma3.siw.service.PiattoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    @Autowired
    private PiattoService piattoService;
    @GetMapping("/{id}")
    public String getMenu(@PathVariable Long id, Model model) {
        Menu menu = menuService.getById(id);
        model.addAttribute("menu", menu);
        return "menu/menu";
    }

    
    @GetMapping("/new")
    public String creaMenu(Model model) {
        model.addAttribute("piatti", piattoService.findAll());
        model.addAttribute("menu", new Menu());
        return "menu/formMenu";
    }
    @GetMapping
    public String getListaMenu(Model model) {
        model.addAttribute("menus", menuService.findAll());
        return "menu/lista";
    }
}
