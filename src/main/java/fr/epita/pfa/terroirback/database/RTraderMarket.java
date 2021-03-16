package fr.epita.pfa.terroirback.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RTraderMarket {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "trader_id", foreignKey = @ForeignKey(name = "fk_trader_id"))
    private Trader trader;

    @ManyToOne
    @JoinColumn(name = "market_id", foreignKey = @ForeignKey(name = "fk_market_id"))
    private Market market;

}
