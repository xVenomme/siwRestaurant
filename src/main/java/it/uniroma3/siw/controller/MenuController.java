package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Menu;

import it.uniroma3.siw.model.MenuTipo;
import it.uniroma3.siw.service.MenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    @GetMapping("/{tipo}")
    public String getMenu(@PathVariable MenuTipo tipo, Model model) {
        Menu menu = menuService.getByTipo(tipo);
        model.addAttribute("menu", menu);
        return "menu";
    }

}
