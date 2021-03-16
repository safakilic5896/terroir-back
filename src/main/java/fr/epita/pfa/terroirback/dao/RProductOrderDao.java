package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.RProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RProductOrderDao extends JpaRepository<RProductOrder, Long> {
}
