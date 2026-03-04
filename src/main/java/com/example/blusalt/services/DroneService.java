package com.example.blusalt.services;

import com.example.blusalt.exceptions.BadRequestException;
import com.example.blusalt.exceptions.NotFoundException;
import com.example.blusalt.models.dtos.*;
import com.example.blusalt.models.entities.Drone;
import com.example.blusalt.models.entities.Medication;
import com.example.blusalt.models.enums.DroneState;
import com.example.blusalt.repositories.DroneRepository;
import com.example.blusalt.repositories.MedicationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ResponseEntity<ApiResponse<DroneResponseDto>> registerDrone(RegisterDroneDto dto) {
        if (droneRepository.existsById(dto.getSerialNumber())) {
            throw new BadRequestException("Drone already registered");
        }

        Drone newDrone = modelMapper.map(dto, Drone.class);

//        Drone newDrone = new Drone();
//        newDrone.setSerialNumber(dto.getSerialNumber());
//        newDrone.setModel(dto.getModel());
//        newDrone.setWeightLimit(dto.getWeightLimit());
//        newDrone.setBatteryCapacity(dto.getBatteryCapacity());
//        newDrone.setState(dto.getState());

        Drone savedDrone = droneRepository.save(newDrone);
        DroneResponseDto response = modelMapper.map(savedDrone, DroneResponseDto.class);

        return ResponseEntity.ok(ResponseBuilder.success("Drone Registered", response));
    }

    @Transactional
    public ResponseEntity<ApiResponse<DroneResponseDto>> loadDrone(String serialNumber, List<MedicationDto> medicationDtos) {
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new NotFoundException("Drone not found"));

        if (drone.getBatteryCapacity() < 25) {
            throw new BadRequestException("Battery level too low (" + drone.getBatteryCapacity() + "%). Minimum 25% required to load.");
        }

        int currentWeight = drone.getLoadedMedications().stream()
                .mapToInt(Medication::getWeight)
                .sum();
        int newItemsWeight = medicationDtos.stream()
                .mapToInt(MedicationDto::getWeight)
                .sum();
        if ((currentWeight + newItemsWeight) > drone.getWeightLimit()) {
            throw new BadRequestException("Weight limit exceeded. Max: " + drone.getWeightLimit() + "g. Current: " + currentWeight + "g.");
        }

        for (MedicationDto medDto : medicationDtos) {
            Medication medication = modelMapper.map(medDto, Medication.class);
            drone.getLoadedMedications().add(medication);
        }

        drone.setState(DroneState.LOADED);

        Drone savedDrone = droneRepository.save(drone);
        DroneResponseDto response = modelMapper.map(savedDrone, DroneResponseDto.class);

        return ResponseEntity.ok(ResponseBuilder.success("Drone Loaded Successfully", response));
    }

    public ResponseEntity<ApiResponse<List<DroneResponseDto>>> getAvailableDrones() {
        List<Drone> availableDrones = droneRepository.findAvailableDronesForLoading();

        List<DroneResponseDto> response = availableDrones.stream()
                .map(drone -> modelMapper.map(drone, DroneResponseDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseBuilder.success("Available drones retrieved", response));
    }

    public ResponseEntity<ApiResponse<List<MedicationResponseDto>>> getDroneMedications(String serialNumber) {
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new NotFoundException("Drone with serial " + serialNumber + " not found"));

        List<MedicationResponseDto> response = drone.getLoadedMedications().stream()
                .map(med -> modelMapper.map(med, MedicationResponseDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseBuilder.success("Medications retrieved successfully", response));
    }

    public ResponseEntity<ApiResponse<Integer>> getDroneBatteryLevel(String serialNumber) {
        Drone drone = droneRepository.findById(serialNumber)
                .orElseThrow(() -> new NotFoundException("Drone not found"));

        return ResponseEntity.ok(ResponseBuilder.success(
                "Battery level retrieved",
                drone.getBatteryCapacity()
        ));
    }
}
