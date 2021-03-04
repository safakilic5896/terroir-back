package fr.epita.pfa.terroirback.database;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
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
