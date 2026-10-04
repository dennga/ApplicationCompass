package de.lokal.applicationcompass.dto;

import de.lokal.applicationcompass.enums.Status;

import java.time.LocalDate;

public record JobApplicationResponse(
        Long id,
        Long companyId,
        String companyName,
        Long locationId,
        String locationCity,
        String position,
        LocalDate appliedAt,
        Status status,
        String contactPerson,
        String jobUrl,
        String notes
) {
}