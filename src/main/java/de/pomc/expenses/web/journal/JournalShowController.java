package de.pomc.expenses.web.journal;

import de.pomc.expenses.journal.Journal;
import de.pomc.expenses.journal.JournalEntry;
import de.pomc.expenses.journal.JournalService;
import de.pomc.expenses.user.User;
import de.pomc.expenses.user.UserService;
import de.pomc.expenses.web.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/journals/{id}")
@RequiredArgsConstructor
public class JournalShowController {

    private final JournalService journalService;
    private final UserService userService;

    @ModelAttribute("users")
    public List<User> getUsers() { return  userService.findAll(); }

    @GetMapping()
    public String showJournal(@PathVariable("id") Long id, Model model) throws NotFoundException {
        Journal journal = journalService.getJournal(id);

        if (journal == null) {
            throw new NotFoundException();
        }

        for (JournalEntry entry: journal.getEntries()) {
            String names = entry.debitorNames();
            System.out.println(names);
        }

        model.addAttribute("journal", journal);
        model.addAttribute("journalEntryForm", new JournalEntryForm());
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
