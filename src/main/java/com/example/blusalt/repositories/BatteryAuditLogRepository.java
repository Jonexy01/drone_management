package com.example.blusalt.repositories;

import com.example.blusalt.models.entities.BatteryAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryAuditLogRepository extends JpaRepository<BatteryAuditLog, Long> {
}
