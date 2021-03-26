package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductOrderDto {

    private long id;

    private String name;

    private String photo;

    private String origin;

    private float price;

    private float amount;

    private String type;

    private MarketBindTraderDto market;
}
