package de.pomc.expenses.web;

import de.pomc.expenses.journal.Journal;
import de.pomc.expenses.journal.JournalEntry;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index() {
        return "redirect:/journals/";
    }
}