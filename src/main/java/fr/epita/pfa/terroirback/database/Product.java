package fr.epita.pfa.terroirback.database;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;

    private String photo;

    private String origine;

    private float price;

    private float stock;

    @Column
    private String description;

    @OneToOne(mappedBy = "product")
    private RProductOrder rProductOrder;

    @ManyToOne
    @JoinColumn(name = "stand_id", foreignKey = @ForeignKey(name = "fk_stand_id"))
    private Stand stand;
}
