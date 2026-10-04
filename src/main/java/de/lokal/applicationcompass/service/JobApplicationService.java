package de.lokal.applicationcompass.service;

import de.lokal.applicationcompass.exceptions.JobApplicationNotFoundException;
import de.lokal.applicationcompass.model.JobApplication;
import de.lokal.applicationcompass.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplication createJobApplication(JobApplication jobApplication) {


    }





}
