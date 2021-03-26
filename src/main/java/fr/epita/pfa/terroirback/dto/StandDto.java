package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandDto {

    private String description;

    private String nom;

    private String typeStand;

    private Long idMarket;

    private String email;
}
