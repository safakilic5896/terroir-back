package fr.epita.pfa.terroirback.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Builder
@Data
public class OpeningTimeDto {
    private long id;

    private String day;

    private LocalTime timeBegin;

    private LocalTime timeEnd;
}
