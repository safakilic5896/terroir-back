package fr.epita.pfa.terroirback.database;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Stand {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "trader_id", foreignKey = @ForeignKey(name = "fk_trader_id"))
    private Trader trader;

    @OneToMany(mappedBy = "stand", cascade = CascadeType.ALL)
    private Set<Product> product;

    @OneToMany(mappedBy = "stand", cascade = CascadeType.ALL)
    private Set<Commande> commande;
}
