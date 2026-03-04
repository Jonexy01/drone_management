package com.example.blusalt.models.entities;

import com.example.blusalt.models.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "medication")
@Entity
public class Medication extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Invalid name format")
    private String name;

    private Integer weight;

    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Invalid code format")
    private String code;

    @Lob
    private byte[] image;
}

