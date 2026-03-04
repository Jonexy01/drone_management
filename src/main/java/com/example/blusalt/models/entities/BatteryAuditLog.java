package com.example.blusalt.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class BatteryAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String droneSerialNumber;

    private Integer batteryLevel;

    private LocalDateTime timestamp;

    public BatteryAuditLog() {}

    public BatteryAuditLog(String serialNumber, Integer batteryLevel) {
        this.droneSerialNumber = serialNumber;
        this.batteryLevel = batteryLevel;
        this.timestamp = LocalDateTime.now();
    }
}
