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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ModelAttribute("journal")
    public Journal getJournal(@PathVariable("id") Long id) {
        Journal journal = journalService.getJournal(id);
        if (journal == null) {
            throw new NotFoundException();
        }
        return journal;
    }

    @GetMapping()
    public String showJournal(@PathVariable("id") Long id, Model model) throws NotFoundException {
        model.addAttribute("journalForm", new JournalForm(getJournal(id)));
        model.addAttribute("journalEntryForm", new JournalEntryForm());
        return "showJournal";
    }

    @PostMapping
    public String changeName(Model model, @PathVariable("id") Long id, @ModelAttribute("journalForm") @Valid JournalForm journalForm,
                             BindingResult bindingResult) {
        Journal journal = getJournal(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("journalEntryForm", new JournalEntryForm());
            return "showJournal";
        }
        journal.name = journalForm.getName();
        journalService.save(journal);

        return "redirect:/journals/" + id;
    }

    @PostMapping(path = "/add")
    public String addEntry(Model model, @PathVariable Long id, @ModelAttribute("journalEntryForm") @Valid JournalEntryForm journalEntryForm,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("journalForm", new JournalForm(getJournal(id)));
            return "showJournal";
        }

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
