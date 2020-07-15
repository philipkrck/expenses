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

    private final JournalRepository repository;
    private final JournalEntryRepository entryRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {

        // add journal for testing
        Journal journal = new Journal("Summer party");
        addJournalEntry(journal, new JournalEntry(new BigDecimal(19), "Drinks", "Olli", Set.of("Philip, Malte")));
        repository.save(journal);
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
            addJournalEntry(journal, entry);
        }
    }

    public void addJournalEntry(Journal journal, JournalEntry entry) {
        entry.setJournal(journal);
        journal.addEntry(entry);
        repository.save(journal);
    }

    public JournalEntry findEntry(Long id) { return entryRepository.findById(id).orElse(null); }

    public void deleteById(Long id) { repository.deleteById(id); }

    public Collection<Journal> findJournals(String search) {
        // ToDo: implement search later
        return repository.findAll();
    }
}
