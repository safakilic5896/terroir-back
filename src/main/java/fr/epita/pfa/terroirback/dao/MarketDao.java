package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketDao extends JpaRepository<Market, Long> {

    public Optional<Market> findById(Long id);

    @Query("SELECT DISTINCT m FROM Market m where (:code_postal is null or m.codePostal = :code_postal)")
    List<Market> findByCodePostal(@Param("code_postal") String codePostal);

    @Query(value = "SELECT DISTINCT m FROM Market m INNER JOIN FETCH m.rTraderMarket p where m.codePostal = :code_postal and p.trader.email = :email")
    List<Market> findByCodePostalAndEmail(@Param("code_postal") String codePostal, @Param("email") String email);

    @Query("SELECT DISTINCT m FROM Market m INNER JOIN FETCH m.product p where p.type = :type")
    List<Market> findByTypeStand(@Param("type") String type);

    @Query("SELECT DISTINCT m FROM Market m INNER JOIN FETCH m.product p  where p.trader.email = :email and p.name LIKE :type")
    List<Market> findByTypeStandAndEmail(@Param("type") String type, @Param("email") String email);

    @Query("SELECT DISTINCT m FROM Market m INNER JOIN FETCH m.product p where p.type = :type and m.codePostal = :code_postal")
    List<Market> findByCodePostalAndType(@Param("code_postal") String codePostal, @Param("type") String type);

    @Query("SELECT DISTINCT m FROM Market m INNER JOIN FETCH m.product p where p.name LIKE :type and m.codePostal = :code_postal and p.trader.email = :email")
    List<Market> findByCodePostalTypeAndEmail(@Param("code_postal") String codePostal, @Param("type") String type, @Param("email") String email);

    @Query("SELECT DISTINCT m FROM Market m INNER JOIN FETCH m.product p where p.trader.email = :email")
    List<Market> findAllByEmail(@Param("email") String email);
}
