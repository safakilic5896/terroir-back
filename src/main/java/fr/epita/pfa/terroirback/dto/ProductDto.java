package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private long idTrader;

    private long idMarket;

    private String name;

    private String origin;

    private float price;

    private float stock;

    private String description;

    private Long IdProduct;

    private String type;

    private String photo;

    private MarketOnly market;

    private boolean actif;

    private String unit;
}
