package fr.epita.pfa.terroirback.dao;

import fr.epita.pfa.terroirback.database.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    @Modifying
    @Query("update Product p set p.actif = false where p.id = :id")
    public void deleteProduct(@Param("id") long id);
}
