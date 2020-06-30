package de.pomc.expenses.journal;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class JournalEntry {
    private BigDecimal amount;

    private String creditor;

    private String description;

    private Set<String> debitors;

    public JournalEntry(BigDecimal amount, String creditor, String description, Set<String> debitors) {
        this.amount = amount;
        this.creditor = creditor;
        this.description = description;
        this.debitors = debitors;
    }

    public Set<String> getDebitors() {
        if (debitors == null){
            debitors = new HashSet<>();
        }
        return debitors;
    }
}
