package de.pomc.expenses.web.journal;

import de.pomc.expenses.journal.JournalEntry;
import de.pomc.expenses.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class JournalEntryForm {

    @NotNull(message = "Please select a creditor.")
    private User creditor;

    @NotBlank(message = "Please enter a description.")
    private String description;

    @NotNull(message = "Please enter an amount.")
    private BigDecimal amount;

    @NotNull(message = "Please select at least one debitor.")
    @Size(min = 1, message = "Please pick at least one debitor.")
    private Set<User> debitors;

    public Set<User> getDebitors() {
        if (debitors == null) {
            debitors = new HashSet<>();
        }
        return debitors;
    }

    public JournalEntryForm(JournalEntry entry) {
        this.creditor = entry.getCreditor();
        this.description = entry.getDescription();
        this.amount = entry.getAmount();
        this.debitors = entry.getDebitors();
    }
}
