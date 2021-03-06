package de.pomc.expenses.journal;

import de.pomc.expenses.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@NoArgsConstructor
@Entity
public class Journal {

    @Getter
    @Id
    @GeneratedValue
    public Long id;

    @Basic(optional = false)
    @Getter
    @Setter
    public String name;

    @Basic(optional = false)

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "journal")
    @OrderBy
    private List<JournalEntry> entries;

    public Journal(String name) {
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

    public Map<User, BigDecimal> computeBalance() {
        // map of the balances sorted by name
        Map<User, BigDecimal> balance = new TreeMap<>();
        for (JournalEntry entry : getEntries()) {
            Set<User> debitors = entry.getDebitors();

            if (debitors.isEmpty()) {
                // ignore entries without debitors
                continue;
            }

            // add balance to creditor
            addToBalance(balance, entry.getCreditor(), entry.getAmount());

            // subtract balance from debitors
            BigDecimal debitorAmount = entry.getAmount()
                    .divide(new BigDecimal(debitors.size()), 4, RoundingMode.HALF_UP).negate();
            for (User debitor : debitors) {
                addToBalance(balance, debitor, debitorAmount);
            }
        }

        // round the result to 2 decimals
        for (Map.Entry<User, BigDecimal> mapEntry : balance.entrySet()) {
            mapEntry.setValue(mapEntry.getValue().setScale(2, RoundingMode.HALF_UP));
        }

        return balance;
    }

    private void addToBalance(Map<User, BigDecimal> balance, User user, BigDecimal amount) {
        BigDecimal currentAmount = balance.getOrDefault(user, BigDecimal.ZERO);
        balance.put(user, currentAmount.add(amount));
    }
}
