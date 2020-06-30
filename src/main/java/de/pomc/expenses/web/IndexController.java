package de.pomc.expenses.web;

import de.pomc.expenses.journal.Journal;
import de.pomc.expenses.journal.JournalEntry;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;

@Controller
public class IndexController {
    Journal journal = new Journal();

    @GetMapping("/")
    public String showUser(Model model) {
        model.addAttribute("journal", journal);
        return "index";
    }

    @PostMapping("/add")
    public String addEntry(@ModelAttribute JournalEntry journalEntry) {
        journal.addEntry(journalEntry);
        return "redirect:/";
    }

}