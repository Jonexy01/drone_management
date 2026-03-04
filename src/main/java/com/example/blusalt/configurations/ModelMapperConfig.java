package com.example.blusalt.configurations;

import com.example.blusalt.models.dtos.MedicationDto;
import com.example.blusalt.models.dtos.MedicationResponseDto;
import com.example.blusalt.models.entities.Medication;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<String, byte[]> toByteArray = context -> {
            if (context.getSource() == null || context.getSource().isEmpty()) return null;
            return java.util.Base64.getDecoder().decode(context.getSource());
        };

        Converter<byte[], String> toBase64 = context -> {
            if (context.getSource() == null) return null;
            return java.util.Base64.getEncoder().encodeToString(context.getSource());
        };

        mapper.createTypeMap(MedicationDto.class, Medication.class)
                .addMappings(m -> m.using(toByteArray)
                        .map(MedicationDto::getImageBase64, Medication::setImage));

        mapper.createTypeMap(Medication.class, MedicationResponseDto.class)
                .addMappings(m -> m.using(toBase64)
                        .map(Medication::getImage, MedicationResponseDto::setImageBase64));

        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper;
    }
}
