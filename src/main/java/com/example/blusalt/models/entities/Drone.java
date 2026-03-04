package com.example.blusalt.models.entities;

import com.example.blusalt.models.Auditable;
import com.example.blusalt.models.enums.DroneModel;
import com.example.blusalt.models.enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "drone")
@Entity
public class Drone extends Auditable {
    @Id
    @Size(max = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Max(value = 500, message = "Weight limit cannot exceed 500gr")
    private Integer weightLimit;

    @Min(0) @Max(100)
    private Integer batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState state;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id")
    private List<Medication> loadedMedications;
}

