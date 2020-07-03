package de.pomc.expenses.journal;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JournalService {

    private JournalRepository repository;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        repository = new JournalRepository();
    }

    public Journal getJournal(Long id) {
        Optional<Journal> optionalJournal = repository.findById(id);
        if (!optionalJournal.isEmpty()) {
            return optionalJournal.get();
        }
        return null;
    }

    public void addJournalEntry(Long journalID, JournalEntry entry) {
        Journal journal = getJournal(journalID);
        if (journal != null) {
            journal.addEntry(entry);
        }
    }

    public Collection<Journal> findJournals(String search) {
        // ToDo: use repository later
        return repository.findAll();
    }
}
