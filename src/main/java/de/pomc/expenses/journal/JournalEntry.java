package de.pomc.expenses.journal;

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

    @Basic(optional = false)
    private String creditor;

    private String description;

    @Transient
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

    public String debitorNames() {
        return getDebitors().stream().sorted().collect(Collectors.joining(", "));
    }
}
