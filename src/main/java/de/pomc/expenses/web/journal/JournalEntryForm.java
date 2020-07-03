package de.pomc.expenses.web.journal;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class JournalEntryForm {

    private String creditor;

    private String description;

    private BigDecimal amount;

    private Set<String> debitors;

    public Set<String> getDebitors() {
        if (debitors == null) {
            debitors = new HashSet<>();
        }
        return debitors;
    }
}
