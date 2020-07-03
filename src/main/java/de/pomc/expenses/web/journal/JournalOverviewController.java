package de.pomc.expenses.web.journal;

import de.pomc.expenses.journal.Journal;
import de.pomc.expenses.journal.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/journals")
@RequiredArgsConstructor
public class JournalOverviewController {

    private final JournalService journalService;

    @GetMapping
    public String journals(Model model) {
        Collection<Journal> journals = journalService.findJournals("");
        model.addAttribute("journals", journals);
        return "journalOverview";
    }
}
