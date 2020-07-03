package de.pomc.expenses.journal;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class JournalRepository {
    private static long nextID;
    Map<Long, Journal> journals;

    public JournalRepository() {
        nextID = 1;
        journals = new HashMap<>();

        Journal journal = new Journal((long) 1, "Summer party");

        journal.addEntry(new JournalEntry(new BigDecimal(10), "Philip", "Essen", Set.of("Malte", "Olli")));
        journal.addEntry(new JournalEntry(new BigDecimal(4), "Malte", "Essen", Set.of("Philip", "Olli")));
        save(journal);
    }

    public Journal save(Journal journal) {
        journals.put(nextID++, journal);
        return journal;
    }

    public Optional<Journal> findById(Long id) {
        return Optional.of(journals.get(id));
    }

    public Collection<Journal> findAll() {
        return journals.values();
    }
}
