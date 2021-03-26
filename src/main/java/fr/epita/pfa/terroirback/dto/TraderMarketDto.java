package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TraderMarketDto {

    private Long idMarket;

    private String email;
}
