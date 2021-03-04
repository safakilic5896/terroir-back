package fr.epita.pfa.terroirback.database;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Trader {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String email;

    private String description;

    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL)
    private Set<Stand> stand;

}
