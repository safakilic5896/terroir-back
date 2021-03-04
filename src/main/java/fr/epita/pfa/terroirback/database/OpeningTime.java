package fr.epita.pfa.terroirback.database;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
public class OpeningTime {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String day;

    private LocalTime timeBegin;

    private LocalTime timeEnd;

    @ManyToOne
    @JoinColumn(name = "market_id", foreignKey = @ForeignKey(name = "fk_market_id"))
    private Market market;
}
