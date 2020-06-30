package de.pomc.expenses.web;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;

@Controller
public class IndexController {

    @GetMapping("/")
    public String showUser() {
        return "index";
    }

}