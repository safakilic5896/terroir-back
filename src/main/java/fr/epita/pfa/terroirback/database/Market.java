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

    private String codePostal;

    private String description;

    private String name;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OpeningTime> openingTime;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> product;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RTraderMarket> rTraderMarket;

}
