package fr.epita.pfa.terroirback.database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;

    private String photo;

    private String origin;

    private float price;

    private float stock;

    @Column
    private String description;

    @OneToOne(mappedBy = "product")
    private RProductOrder rProductOrder;

    @ManyToOne
    @JoinColumn(name = "trader_id", foreignKey = @ForeignKey(name = "fk_trader_id"))
    private Trader trader;

    @ManyToOne
    @JoinColumn(name = "market_id", foreignKey = @ForeignKey(name = "fk_market_id"))
    private Market market;

    private String type;
}
