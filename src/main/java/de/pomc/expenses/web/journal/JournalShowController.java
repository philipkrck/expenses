package de.pomc.expenses.web.journal;

import de.pomc.expenses.journal.Journal;
import de.pomc.expenses.journal.JournalEntry;
import de.pomc.expenses.journal.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/journals/{id}")
@RequiredArgsConstructor
public class JournalShowController {

    private final JournalService journalService;

    @GetMapping()
    public String showJournal(@PathVariable("id") Long id, Model model) {
        Journal journal = journalService.getJournal(id);
        model.addAttribute("journal", journal);
        return "showJournal";
    }

    @PostMapping(path = "/add")
    public String addEntry(@PathVariable Long id, @ModelAttribute("journalEntryForm") JournalEntryForm journalEntryForm) {
        JournalEntry journalEntry = JournalFormConverter.shared.journalEntry(journalEntryForm);
        journalService.addJournalEntry(id, journalEntry);
        return "redirect:/journals/" + id;
    }

    @PostMapping(path = "/delete")
    public String deleteEntry(Model model, @PathVariable Long id) {
        journalService.deleteById(id);
        return "redirect:/journals/";
    }
}
