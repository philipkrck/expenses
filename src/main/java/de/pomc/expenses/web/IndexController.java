package de.pomc.expenses.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @RequestParam(name = "hello", required = false) String hello) {
        if (hello != null) {
            model.addAttribute("greeting", "Hello, " + hello + "!");
        }
        return "index";
    }
}
