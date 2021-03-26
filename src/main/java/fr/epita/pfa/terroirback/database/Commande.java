package fr.epita.pfa.terroirback.database;

import lombok.*;
import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime dateTimeReservation;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDate dateOrder;

    private boolean validate;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime dateValidate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_customer_id"))
    private Customer customer;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RProductOrder> rProductOrder;

    @ManyToOne
    @JoinColumn(name = "trader_id", foreignKey = @ForeignKey(name = "fk_trader_id"))
    private Trader trader;

    @OneToOne(targetEntity = Market.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "market_id")
    private Market market;
}
