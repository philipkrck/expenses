package de.pomc.expenses.web.journal;

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
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/editEntry/{id}")
public class EditJournalController {

    private final JournalService journalService;
    private final UserService userService;

    @ModelAttribute("currentID")
    public Long currentId(@PathVariable("id") Long id) {
        return id;
    }

    private JournalEntry journalEntry(Long id) {
        JournalEntry entry = journalService.findEntry(id);
        if (entry == null) {
            throw new NotFoundException();
        }
        return entry;
    }

    private Long journalID(@PathVariable("id") Long entryId) {
        return journalEntry(entryId).getJournal().getId();
    }

    @ModelAttribute("users")
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping
    public String showEditEntry(@PathVariable("id") Long id, Model model) {
        model.addAttribute("journalEntryForm", new JournalEntryForm(journalEntry(id)));
        return "editEntry";
    }

    @PostMapping("/update")
    public String updateEntry(@PathVariable("id") Long id, Model model, @ModelAttribute("journalEntryForm") @Valid JournalEntryForm journalEntryForm,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editEntry";
        }

        JournalEntry entry = journalEntry(id);
        entry.setAmount(journalEntryForm.getAmount());
        entry.setCreditor(journalEntryForm.getCreditor());
        entry.setDescription(journalEntryForm.getDescription());
        entry.setDebitors((journalEntryForm.getDebitors()));

        journalService.save(entry.getJournal());

        return "redirect:/journals/" + journalID(id);
    }
}
