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

    @Query("SELECT DISTINCT m FROM Market m INNER JOIN FETCH m.product p where p.type = :type")
    List<Market> findByTypeStand(@Param("type") String type);

    @Query("SELECT DISTINCT m FROM Market m INNER JOIN FETCH m.product p where p.type = :type and m.codePostal = :code_postal")
    List<Market> findByCodePostalAndType(@Param("code_postal") String codePostal, @Param("type") String type);
}
