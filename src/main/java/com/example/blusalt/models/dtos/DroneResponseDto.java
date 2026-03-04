package com.example.blusalt.models.dtos;

import com.example.blusalt.models.entities.Medication;
import com.example.blusalt.models.enums.DroneModel;
import com.example.blusalt.models.enums.DroneState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DroneResponseDto extends AuditableDateResponseDto {
    private String serialNumber;
    private DroneModel model;
    private Integer weightLimit;
    private Integer batteryCapacity;
    private DroneState state;
    private List<Medication> loadedMedications;
}
