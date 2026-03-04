package com.example.blusalt.services;

import com.example.blusalt.models.entities.BatteryAuditLog;
import com.example.blusalt.repositories.BatteryAuditLogRepository;
import com.example.blusalt.repositories.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BatteryCheckScheduler {

    private static final Logger log = LoggerFactory.getLogger(BatteryCheckScheduler.class);

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private BatteryAuditLogRepository auditLogRepository;

    // Runs every 30 seconds (30,000 milliseconds)
    @Scheduled(fixedRate = 30000)
    public void checkAndLogBatteryLevels() {
        log.info("Starting periodic battery level audit...");

        droneRepository.findAll().forEach(drone -> {
            BatteryAuditLog audit = new BatteryAuditLog(
                    drone.getSerialNumber(),
                    drone.getBatteryCapacity()
            );

            auditLogRepository.save(audit);

            log.debug("Logged battery for drone {}: {}%",
                    drone.getSerialNumber(),
                    drone.getBatteryCapacity());
        });

        log.info("Battery audit completed for all drones.");
    }
}
