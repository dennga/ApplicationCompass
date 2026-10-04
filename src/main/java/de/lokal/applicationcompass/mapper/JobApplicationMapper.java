package de.lokal.applicationcompass.mapper;

import de.lokal.applicationcompass.dto.JobApplicationResponse;
import de.lokal.applicationcompass.model.Company;
import de.lokal.applicationcompass.model.JobApplication;
import de.lokal.applicationcompass.model.Location;
import org.springframework.stereotype.Component;

/**
 * Reine Umwandlung Entity <-> DTO, ohne Datenbankzugriff.
 * Das Auflösen von companyId/locationId zu Company/Location
 * passiert bewusst nicht hier, sondern im Controller bzw. Service.
 */
@Component
public class JobApplicationMapper {

    public JobApplicationResponse toResponse(final JobApplication jobApplication) {
        final Company company = jobApplication.getCompany();
        final Location location = jobApplication.getLocation();

        return new JobApplicationResponse(
                jobApplication.getId(),
                company != null ? company.getId() : null,
                company != null ? company.getName() : null,
                location != null ? location.getId() : null,
                location != null ? location.getCity() : null,
                jobApplication.getPosition(),
                jobApplication.getAppliedAt(),
                jobApplication.getStatus(),
                jobApplication.getContactPerson(),
                jobApplication.getJobUrl(),
                jobApplication.getNotes()
        );
    }

    public JobApplication toNewEntity(final de.lokal.applicationcompass.dto.CreateJobApplicationRequest request,
                                      final Company company, final Location location) {
        final JobApplication jobApplication = new JobApplication();
        jobApplication.setCompany(company);
        jobApplication.setLocation(location);
        jobApplication.setPosition(request.position());
        jobApplication.setAppliedAt(request.appliedAt());
        jobApplication.setContactPerson(request.contactPerson());
        jobApplication.setJobUrl(request.jobUrl());
        jobApplication.setNotes(request.notes());
        return jobApplication;
    }

    public JobApplication toUpdatedData(final de.lokal.applicationcompass.dto.UpdateJobApplicationRequest request,
                                        final Location location) {
        final JobApplication jobApplication = new JobApplication();
        jobApplication.setLocation(location);
        jobApplication.setPosition(request.position());
        jobApplication.setAppliedAt(request.appliedAt());
        jobApplication.setStatus(request.status());
        jobApplication.setContactPerson(request.contactPerson());
        jobApplication.setJobUrl(request.jobUrl());
        jobApplication.setNotes(request.notes());
        return jobApplication;
    }
}