package com.example.blusalt.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationDto {
    @NotBlank(message = "Medication name is required")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$",
            message = "Name can only contain letters, numbers, '-' and '_'")
    private String name;

    @NotNull(message = "Weight is required")
    @Min(value = 1, message = "Weight must be at least 1g")
    private Integer weight;

    @NotBlank(message = "Medication code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "Code must be upper case letters, underscore and numbers only")
    private String code;

    @NotBlank(message = "Image is required")
    private String imageBase64;
}
