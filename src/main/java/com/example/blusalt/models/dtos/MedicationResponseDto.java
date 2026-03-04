package com.example.blusalt.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationResponseDto extends AuditableDateResponseDto {
    private String name;
    private Integer weight;
    private String code;
    private String imageBase64;
}
