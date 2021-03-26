package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderDao extends JpaRepository<Commande, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Commande SET validate = true where id = :idCommande")
    public void updateValidate(@Param("idCommande") long idCommande);

}
