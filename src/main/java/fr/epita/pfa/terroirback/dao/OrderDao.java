package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Commande, Long> {

}
