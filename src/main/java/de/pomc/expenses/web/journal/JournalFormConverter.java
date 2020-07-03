package de.pomc.expenses.web.journal;

import de.pomc.expenses.journal.JournalEntry;

public class JournalFormConverter {

    public static JournalFormConverter shared = new JournalFormConverter();

    public JournalEntry journalEntry(JournalEntryForm form) {
        return new JournalEntry(form.getAmount(), form.getCreditor(), form.getDescription(), form.getDebitors());
    }

}
