package fr.epita.pfa.terroirback.database;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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
