package de.lokal.applicationcompass.dto;

import de.lokal.applicationcompass.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateJobApplicationRequest(

        Long locationId,

        @NotBlank
        @Size(max = 200)
        String position,

        @NotNull
        @PastOrPresent
        LocalDate appliedAt,

        @NotNull
        Status status,

        @Size(max = 150)
        String contactPerson,

        @Size(max = 500)
        String jobUrl,

        String notes
) {
}