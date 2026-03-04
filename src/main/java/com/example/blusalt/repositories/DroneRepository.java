package com.example.blusalt.repositories;

import com.example.blusalt.models.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    // Requirement: State must be IDLE and Battery >= 25%
    @Query("SELECT d FROM Drone d WHERE d.state = 'IDLE' AND d.batteryCapacity >= 25")
    List<Drone> findAvailableDronesForLoading();
}
