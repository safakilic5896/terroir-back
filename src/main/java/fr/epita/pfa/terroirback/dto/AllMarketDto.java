package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AllMarketDto {
    private long id;

    private String adress;

    private String city;

    private String codePostal;

    private String description;

    private String name;

    private List<OpeningTimeDto> openingTimeDto;

    private List<TraderDto> traderDto;
}
