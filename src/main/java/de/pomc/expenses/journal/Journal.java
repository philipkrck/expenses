package de.pomc.expenses.journal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@NoArgsConstructor
public class Journal {

    @Getter
    public Long id;

    public String name;

    private List<JournalEntry> entries;

    public Journal(Long id, String name) {
        this.id = id;
        this.name = name;
        this.entries = new ArrayList<>();
    }

    public List<JournalEntry> getEntries() {
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }

    public void addEntry(JournalEntry entry) {
        entries.add(entry);
    }

    public Map<String, BigDecimal> computeBalance() {
        Map<String, BigDecimal> balance = new TreeMap<>();
        for (JournalEntry entry : getEntries()) {
            Set<String> debitors = entry.getDebitors();

            if (debitors.isEmpty()) {
                continue; // ignore entries without debitors
            }

            addAmountToUser(balance, entry.getCreditor(), entry.getAmount()); // add amount to creditor

            BigDecimal debitorAmount = entry.getAmount()
                                            .divide(new BigDecimal(debitors.size()), 4, RoundingMode.HALF_UP).negate();

            for (String debitor : debitors) {
                addAmountToUser(balance, debitor, debitorAmount); // add loans to debitors
            }
        }

        // round result to 2 decimals
        for (Map.Entry<String, BigDecimal> mapEntry : balance.entrySet()) {
            mapEntry.setValue(mapEntry.getValue().setScale(2, RoundingMode.HALF_UP));
        }

        return balance;
    }

    private void addAmountToUser(Map<String, BigDecimal> balance, String user, BigDecimal amount) {
        BigDecimal currentAmount = balance.getOrDefault(user, BigDecimal.ZERO);
        balance.put(user, currentAmount.add(amount));
    }
}
