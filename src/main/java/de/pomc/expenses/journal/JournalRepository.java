package de.pomc.expenses.journal;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JournalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Journal journal) {
        this.entityManager.persist(journal);
    }

    public void deleteById(Long id) {
        this.entityManager.remove(findById(id));
    }

    public Optional<Journal> findById(Long id) {
        Journal journal = this.entityManager.createQuery("select j from Journal j where j.id = :id", Journal.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.of(journal);
    }

    public Collection<Journal> findAll() {
        return this.entityManager.createQuery("select j from Journal j", Journal.class)
                .getResultList();
    }

}
