package com.example.blusalt.models.dtos;

import com.example.blusalt.models.entities.Medication;
import com.example.blusalt.models.enums.DroneModel;
import com.example.blusalt.models.enums.DroneState;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDroneDto {
    @NotBlank(message = "Serial Number is required")
    @Size(max = 100, message = "Serial Number must be 100 characters max")
    private String serialNumber;

    @NotNull(message = "Model is required")
    private DroneModel model;

    @NotNull(message = "Weight limit is required")
    @Max(value = 500, message = "Weight limit cannot exceed 500gr")
    @Positive(message = "Weight limit must be a positive number")
    private Integer weightLimit;

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery cannot be less than 0%")
    @Max(value = 100, message = "Battery cannot exceed 100%")
    private Integer batteryCapacity;

    @NotNull(message = "Initial state is required")
    private DroneState state;
}
