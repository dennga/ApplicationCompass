package de.lokal.applicationcompass.controller;

import de.lokal.applicationcompass.dto.CreateJobApplicationRequest;
import de.lokal.applicationcompass.dto.JobApplicationResponse;
import de.lokal.applicationcompass.dto.UpdateJobApplicationRequest;
import de.lokal.applicationcompass.model.JobApplication;
import de.lokal.applicationcompass.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationResponse> create(@Valid @RequestBody final CreateJobApplicationRequest request) {
        final JobApplication jobApplication = new JobApplication();
        jobApplication.setCompanyName(request.companyName());
        jobApplication.setCity(request.city());
        jobApplication.setPosition(request.position());
        jobApplication.setAppliedAt(request.appliedAt());
        jobApplication.setContactPerson(request.contactPerson());
        jobApplication.setJobUrl(request.jobUrl());
        jobApplication.setNotes(request.notes());

        final JobApplication saved = jobApplicationService.createJobApplication(jobApplication);
        return ResponseEntity.status(HttpStatus.CREATED).body(JobApplicationResponse.from(saved));
    }

    @GetMapping("/{id}")
    public JobApplicationResponse getById(@PathVariable final Long id) {
        return JobApplicationResponse.from(jobApplicationService.readJobApplicationById(id));
    }

    @GetMapping
    public List<JobApplicationResponse> getAll() {
        return jobApplicationService.readAllJobApplications().stream()
                .map(JobApplicationResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public JobApplicationResponse update(@PathVariable final Long id,
                                         @Valid @RequestBody final UpdateJobApplicationRequest request) {
        final JobApplication updatedData = new JobApplication();
        updatedData.setCompanyName(request.companyName());
        updatedData.setCity(request.city());
        updatedData.setPosition(request.position());
        updatedData.setAppliedAt(request.appliedAt());
        updatedData.setStatus(request.status());
        updatedData.setContactPerson(request.contactPerson());
        updatedData.setJobUrl(request.jobUrl());
        updatedData.setNotes(request.notes());

        return JobApplicationResponse.from(jobApplicationService.updateJobApplication(id, updatedData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        jobApplicationService.deleteJobApplication(id);
        return ResponseEntity.noContent().build();
    }
}