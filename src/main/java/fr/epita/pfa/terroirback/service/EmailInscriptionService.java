package fr.epita.pfa.terroirback.service;

import fr.epita.pfa.terroirback.dao.CustomerDao;
import fr.epita.pfa.terroirback.dao.TraderDao;
import fr.epita.pfa.terroirback.database.Customer;
import fr.epita.pfa.terroirback.database.Trader;
import fr.epita.pfa.terroirback.dto.EmailInscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmailInscriptionService {

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private CustomerDao customerDao;

    public void postEmail(EmailInscription emailInscription) throws Exception {
        if(emailInscription.getRole().equals("CLIENT")) {
            try {
                customerDao.save(Customer.builder().city(emailInscription.getCity())
                        .codePostal(emailInscription.getCodePostal()).email(emailInscription.getEmail()).build());
            } catch (Exception e) {
                throw new Exception(e);
            }
        } else {
            try {
                traderDao.save(Trader.builder().email(emailInscription.getEmail()).build());
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }
}
