package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MarketBindTraderDto {

    private Long idMarket;

    private Long idTrader;

    private String adress;

    private String city;

    private String codePostal;

    private String description;

    private String name;

    private List<OpeningTimeDto> openingTimeDto;
}
