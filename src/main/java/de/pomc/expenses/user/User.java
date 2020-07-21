package de.pomc.expenses.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements Comparable<User> {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;

    @Basic(optional = false)
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


    // MARK: - Comparable implementation
    @Override
    public int compareTo(User otherUser) {
        return this.name.compareTo(otherUser.name);
    }
}
