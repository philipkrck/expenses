package de.pomc.expenses.web.journal;

import de.pomc.expenses.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class JournalEntryForm {

    private User creditor;

    @Size.List({
        @Size(min = 3, message = "Please enter at least 3 characters."),
        @Size(max = 255, message = "Please enter at most 255 characters.")
    })
    private String description;

    @Size(min = 0, message = "Please enter only numbers greater than zero.")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "some message")
    private BigDecimal amount;

    private Set<User> debitors;

    public Set<User> getDebitors() {
        if (debitors == null) {
            debitors = new HashSet<>();
        }
        return debitors;
    }
}
