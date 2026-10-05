package de.lokal.applicationcompass.dto;

import de.lokal.applicationcompass.enums.Status;
import de.lokal.applicationcompass.model.JobApplication;

import java.time.LocalDate;

public record JobApplicationResponse(
        Long id,
        String companyName,
        String city,
        String position,
        LocalDate appliedAt,
        Status status,
        String contactPerson,
        String jobUrl,
        String notes
) {

    public static JobApplicationResponse from(final JobApplication entity) {
        return new JobApplicationResponse(
                entity.getId(),
                entity.getCompanyName(),
                entity.getCity(),
                entity.getPosition(),
                entity.getAppliedAt(),
                entity.getStatus(),
                entity.getContactPerson(),
                entity.getJobUrl(),
                entity.getNotes()
        );
    }
}