package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommandeDto {

    private Long idTrader;

    private float price;

    private Long IdProduct;

    private float amount;

    private LocalDateTime dateOrder;

    private LocalDateTime dateReservation;

    private Long idMarket;
}
