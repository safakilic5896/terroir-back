package fr.epita.pfa.terroirback.database;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Trader {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String email;

    private String description;

    private String name;

    private String fname;

    private String phoneNumber;

    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> product;

    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Commande> commande;

    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RTraderMarket> RTraderMarket;

}
