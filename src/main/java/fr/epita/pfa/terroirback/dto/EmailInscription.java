package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailInscription {

    private String city;

    private int codePostal;

    private String email;

    private String role;
}
