package de.pomc.expenses.web.journal;

import de.pomc.expenses.journal.Journal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class JournalForm {

    @Size.List({
        @Size(min = 3, message = "Please enter at least 3 characters."),
        @Size(max = 255, message = "Please enter at most 255 characters")
    })
    private String name;

    public JournalForm(Journal journal) {
        this.name = journal.getName();
    }
}
