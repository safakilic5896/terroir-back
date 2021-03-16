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
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String city;

    private String codePostal;

    private String email;

    private String name;

    private String fname;

    private String phoneNumber;

    private String idMarkets;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Commande> commande;
}
