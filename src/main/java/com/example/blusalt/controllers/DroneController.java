package com.example.blusalt.controllers;

import com.example.blusalt.models.dtos.*;
import com.example.blusalt.services.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/drones")
public class DroneController {
    @Autowired
    private DroneService droneService;

    @Operation(summary = "Create drone", description = "Registers a new drone")
    @PostMapping("register")
    public ResponseEntity<ApiResponse<DroneResponseDto>> registerDrone(@Valid @RequestBody RegisterDroneDto dto) {
        return droneService.registerDrone(dto);
    }

    @Operation(summary = "Load drone", description = "Loads medications onto a specific drone")
    @PostMapping("/{serialNumber}/load")
    public ResponseEntity<ApiResponse<DroneResponseDto>> loadDrone(
            @PathVariable String serialNumber,
            @Valid @RequestBody List<MedicationDto> medications) {
        return droneService.loadDrone(serialNumber, medications);
    }

    @Operation(summary = "Get available drones", description = "Returns a list of drones in IDLE state with battery above 25%")
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<DroneResponseDto>>> getAvailableDrones() {
        return droneService.getAvailableDrones();
    }

    @Operation(summary = "Check loaded medications", description = "Returns a list of medications currently loaded on a specific drone")
    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<ApiResponse<List<MedicationResponseDto>>> getDroneMedications(@PathVariable String serialNumber) {
        return droneService.getDroneMedications(serialNumber);
    }

    @Operation(summary = "Check battery level", description = "Returns the battery percentage for a specific drone")
    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<ApiResponse<Integer>> getDroneBatteryLevel(@PathVariable String serialNumber) {
        return droneService.getDroneBatteryLevel(serialNumber);
    }
}
