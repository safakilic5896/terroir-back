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

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RProductOrder> rProductOrder;

    @ManyToOne
    @JoinColumn(name = "trader_id", foreignKey = @ForeignKey(name = "fk_trader_id"))
    private Trader trader;
}
