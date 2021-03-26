package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.RTraderMarket;
import fr.epita.pfa.terroirback.database.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface TraderDao extends JpaRepository<Trader, Long> {

    public Optional<Trader> findByEmail(String email);

    @Query("SELECT DISTINCT t from Trader t INNER JOIN fetch t.commande c WHERE t.email = :email AND c.dateOrder = :date")
    public Optional<Trader> findByEmailAndCommandeDate(@Param("email") String email, @Param("date") LocalDate date);

    @Query("SELECT DISTINCT t FROM Trader t INNER JOIN FETCH t.commande c where t.email =:email AND c.dateOrder =:date AND c.market.id =:id ")
    public Optional<Trader> findByEmailIdAndDate(@Param("email") String email, @Param("id")long id, @Param("date") LocalDate date);

    @Query("SELECT DISTINCT t FROM Trader t INNER JOIN FETCH t.commande c where c.market.id =:id AND t.email =:email")
    public Optional<Trader> findByEmailAndIdMarket(@Param("email") String email, @Param("id")long id);




}
