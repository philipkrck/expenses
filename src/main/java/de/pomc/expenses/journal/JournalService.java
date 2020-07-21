package de.pomc.expenses.journal;

import de.pomc.expenses.user.User;
import de.pomc.expenses.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository repository;
    private final JournalEntryRepository entryRepository;
    private final UserService userService;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        if (!userService.findAll().isEmpty()) {
            // prevent initialization if DB is not empty
            return;
        }
        userService.init();
        List<User> users = userService.findAll();
        User userOne = users.get(0);
        User userTwo = users.get(1);
        User userThree = users.get(2);

        Journal journal = new Journal("Summer party");
        addJournalEntry(journal, new JournalEntry(new BigDecimal(19), userThree, "Drinks", Set.of(userOne, userTwo)));
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
    }

    public JournalEntry findEntry(Long id) { return entryRepository.findById(id).orElse(null); }

    public void deleteById(Long id) { repository.deleteById(id); }

    public Collection<Journal> findJournals(String search) {
        // ToDo: implement search later
        return repository.findAll();
    }
}
