package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.RTraderMarket;
import fr.epita.pfa.terroirback.database.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RTraderMarketDao extends JpaRepository<RTraderMarket, Long> {

    Optional<RTraderMarket> findRTraderMarketByTrader(Trader trader);
}
