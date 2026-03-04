package com.example.blusalt.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditableDateResponseDto {
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
