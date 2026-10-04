package de.lokal.applicationcompass.service;

import de.lokal.applicationcompass.exceptions.JobApplicationNotFoundException;
import de.lokal.applicationcompass.exceptions.LocationMismatchException;
import de.lokal.applicationcompass.model.JobApplication;
import de.lokal.applicationcompass.model.Location;
import de.lokal.applicationcompass.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    public static final String NOT_FOUND_WITH_ID = "Job application not found with id: ";
    public static final String LOCATION_MISMATCH = "Location does not belong to the application's company";

    private final JobApplicationRepository jobApplicationRepository;

    @Transactional
    public JobApplication createJobApplication(final JobApplication jobApplication) {
        validateLocationBelongsToCompany(jobApplication);
        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication readJobApplicationById(final Long id) {
        return jobApplicationRepository.findById(id).orElseThrow(() ->
                new JobApplicationNotFoundException(NOT_FOUND_WITH_ID + id));
    }

    public List<JobApplication> readAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    @Transactional
    public JobApplication updateJobApplication(final Long id, final JobApplication updatedData) {
        final JobApplication existing = readJobApplicationById(id);
        applyUpdatableFields(existing, updatedData);
        validateLocationBelongsToCompany(existing);
        return jobApplicationRepository.save(existing);
    }

    @Transactional
    public void deleteJobApplication(final Long id) {
        if (!jobApplicationRepository.existsById(id)) {
            throw new JobApplicationNotFoundException(NOT_FOUND_WITH_ID + id);
        }
        jobApplicationRepository.deleteById(id);
    }

    private void applyUpdatableFields(final JobApplication existing, final JobApplication updatedData) {
        existing.setPosition(updatedData.getPosition());
        existing.setAppliedAt(updatedData.getAppliedAt());
        existing.setStatus(updatedData.getStatus());
        existing.setLocation(updatedData.getLocation());
        existing.setContactPerson(updatedData.getContactPerson());
        existing.setJobUrl(updatedData.getJobUrl());
        existing.setNotes(updatedData.getNotes());
    }

    private void validateLocationBelongsToCompany(final JobApplication jobApplication) {
        final Location location = jobApplication.getLocation();
        if (location == null) {
            return;
        }
        if (!location.getCompany().getId().equals(jobApplication.getCompany().getId())) {
            throw new LocationMismatchException(LOCATION_MISMATCH);
        }
    }
}