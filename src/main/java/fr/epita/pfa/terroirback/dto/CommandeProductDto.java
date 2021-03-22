package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommandeProductDto {

    private long id;

    private float totalPrice;

    private LocalDateTime dateTimeReservation;

    private LocalDateTime dateOrder;

    private boolean validate;

    private LocalDateTime dateValidate;

    private TraderDto trader;

    private List<ProductOrderDto> product;
}
