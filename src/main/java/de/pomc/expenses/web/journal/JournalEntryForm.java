package de.pomc.expenses.web.journal;

import de.pomc.expenses.user.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class JournalEntryForm {

    private User creditor;

    private String description;

    private BigDecimal amount;

    private Set<User> debitors;

    public Set<User> getDebitors() {
        if (debitors == null) {
            debitors = new HashSet<>();
        }
        return debitors;
    }
}
