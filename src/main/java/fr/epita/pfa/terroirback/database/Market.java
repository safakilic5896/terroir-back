package fr.epita.pfa.terroirback.database;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Market {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String adress;

    private String city;

    private int codePostal;

    private String description;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private Set<OpeningTime> openingTime;
}
