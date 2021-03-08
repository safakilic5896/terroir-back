package fr.epita.pfa.terroirback.database;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class RProductOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false, foreignKey = @ForeignKey(name = "fk_commande_id"))
    private Commande commande;

    private float amont;

    private float price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_id"))
    private Product product;
}