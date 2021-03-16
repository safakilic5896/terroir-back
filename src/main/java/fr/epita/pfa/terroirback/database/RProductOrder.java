package fr.epita.pfa.terroirback.database;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RProductOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false, foreignKey = @ForeignKey(name = "fk_commande_id"))
    private Commande commande;

    private float amont;

    private float price;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_id"))
    private Product product;
}