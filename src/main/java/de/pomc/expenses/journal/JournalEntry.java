package de.pomc.expenses.journal;

import de.pomc.expenses.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class JournalEntry {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(optional = false)
    private Journal journal;

    @Basic(optional = false)
    private BigDecimal amount;

    @ManyToOne(optional = false)
    private User creditor;

    private String description;

    @ManyToMany
    private Set<User> debitors;

    public JournalEntry(BigDecimal amount, User creditor, String description, Set<User> debitors) {
        this.amount = amount;
        this.creditor = creditor;
        this.description = description;
        this.debitors = debitors;
    }

    public Set<User> getDebitors() {
        if (debitors == null){
            debitors = new HashSet<>();
        }
        return debitors;
    }

    public String debitorNames() {
        return getDebitors().stream().sorted().map(User::getName).collect(Collectors.joining(", "));
    }
}
