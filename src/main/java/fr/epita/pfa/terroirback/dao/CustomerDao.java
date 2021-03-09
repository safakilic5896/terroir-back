package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {

}
