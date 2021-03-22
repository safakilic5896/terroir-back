package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraderDao extends JpaRepository<Trader, Long> {

    public Optional<Trader> findByEmail(String email);




}
