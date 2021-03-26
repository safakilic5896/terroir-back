package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.Market;
import fr.epita.pfa.terroirback.database.RTraderMarket;
import fr.epita.pfa.terroirback.database.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RTraderMarketDao extends JpaRepository<RTraderMarket, Long> {

    Optional<RTraderMarket> findRTraderMarketByTrader(Trader trader);

    @Query("SELECT r from RTraderMarket r WHERE r.trader.id=:trader and r.market.id=:market")
    Optional<RTraderMarket> findRTraderMarketsByTraderAndMarket(@Param("trader") long trader, @Param("market") long market);
}
