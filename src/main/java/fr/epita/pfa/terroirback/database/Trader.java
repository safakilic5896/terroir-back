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
public class Trader {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String email;

    private String description;

    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL)
    private Set<Stand> stand;

}
