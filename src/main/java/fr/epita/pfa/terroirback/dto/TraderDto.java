package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TraderDto {

    private long id;

    private String email;

    private String description;

    private ProductDto product;

    private String name;

    private String fname;

    private String phoneNumber;
}
