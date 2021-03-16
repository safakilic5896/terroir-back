package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarketOnly {

    private long id;

    private String adress;

    private String city;

    private String codePostal;

    private String description;

    private String name;
}
