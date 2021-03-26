package fr.epita.pfa.terroirback.service;

import fr.epita.pfa.terroirback.dao.CustomerDao;
import fr.epita.pfa.terroirback.dao.MarketDao;
import fr.epita.pfa.terroirback.dao.RTraderMarketDao;
import fr.epita.pfa.terroirback.dao.TraderDao;
import fr.epita.pfa.terroirback.database.Customer;
import fr.epita.pfa.terroirback.database.Market;
import fr.epita.pfa.terroirback.database.RTraderMarket;
import fr.epita.pfa.terroirback.database.Trader;
import fr.epita.pfa.terroirback.dto.EmailInscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class EmailInscriptionService {

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private MarketDao marketDao;

    @Autowired
    private RTraderMarketDao rTraderMarketDao;

    public void postEmail(EmailInscription emailInscription) throws Exception {
        if(emailInscription.getRole().equals("CLIENT")) {
            try {
                customerDao.save(Customer.builder().city(emailInscription.getCity())
                        .codePostal(emailInscription.getCodePostal()).email(emailInscription.getEmail()).fname(emailInscription.getFname()).name(emailInscription.getName())
                        .phoneNumber(emailInscription.getPhoneNumber()).idMarkets(emailInscription.getIdMarkets()).build());
            } catch (Exception e) {
                throw new Exception(e);
            }
        } else {
            try {
                List<Long> idMarkets = Stream.of(emailInscription.getIdMarkets().split(",")).map(Long::parseLong).collect(Collectors.toList());
                List<Market> marketList = marketDao.findAllById(idMarkets);
                Trader trader  = traderDao.saveAndFlush(Trader.builder().email(emailInscription.getEmail()).fname(emailInscription.getFname()).name(emailInscription.getName())
                        .phoneNumber(emailInscription.getPhoneNumber()).build());
                marketList.forEach(market -> {
                    rTraderMarketDao.save(RTraderMarket.builder().trader(trader).market(market).build());
                });
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }
}
