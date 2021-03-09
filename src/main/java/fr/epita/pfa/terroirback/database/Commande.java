package fr.epita.pfa.terroirback.database;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Commande {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private float totalPrice;

    @Column
    private LocalDateTime dateTimeReservation;

    private LocalDateTime dateOrder;

    private boolean validate;

    @Column
    private LocalDateTime dateValidate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_customer_id"))
    private Customer customer;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private Set<RProductOrder> rProductOrder;

    @ManyToOne
    @JoinColumn(name = "stand_id", foreignKey = @ForeignKey(name = "stand_id"))
    private Stand stand;
}
