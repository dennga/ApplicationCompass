package de.lokal.applicationcompass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateJobApplicationRequest(

        @NotNull
        Long companyId,

        Long locationId,

        @NotBlank
        @Size(max = 200)
        String position,

        @NotNull
        @PastOrPresent
        LocalDate appliedAt,

        @Size(max = 150)
        String contactPerson,

        @Size(max = 500)
        String jobUrl,

        String notes
) {
}