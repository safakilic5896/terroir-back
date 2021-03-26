package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerOrderDto {

    private long id;

    private String city;

    private String codePostal;

    private String email;

    private String name;

    private String fname;

    private String phoneNumber;

    private List<CommandeProductDto> commandeProductDto;
}
