package de.lokal.applicationcompass.service;

import de.lokal.applicationcompass.enums.Status;
import de.lokal.applicationcompass.exceptions.JobApplicationNotFoundException;
import de.lokal.applicationcompass.model.JobApplication;
import de.lokal.applicationcompass.repository.JobApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @InjectMocks
    private JobApplicationService jobApplicationService;

    @Test
    void testCreateJobApplicationSavesAndReturnsEntity() {
        final JobApplication jobApplication = newJobApplication();
        when(jobApplicationRepository.save(jobApplication)).thenReturn(jobApplication);

        final JobApplication result = jobApplicationService.createJobApplication(jobApplication);

        assertThat(result).isEqualTo(jobApplication);
        verify(jobApplicationRepository).save(jobApplication);
    }

    @Test
    void testReadJobApplicationByIdReturnsEntityWhenFound() {
        final JobApplication jobApplication = newJobApplication();
        when(jobApplicationRepository.findById(5L)).thenReturn(Optional.of(jobApplication));

        final JobApplication result = jobApplicationService.readJobApplicationById(5L);

        assertThat(result).isEqualTo(jobApplication);
    }

    @Test
    void testReadJobApplicationByIdThrowsWhenNotFound() {
        when(jobApplicationRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> jobApplicationService.readJobApplicationById(5L))
                .isInstanceOf(JobApplicationNotFoundException.class)
                .hasMessage(JobApplicationService.NOT_FOUND_WITH_ID + 5L);
    }

    @Test
    void testReadAllJobApplicationsReturnsAllEntries() {
        final JobApplication first = newJobApplication();
        final JobApplication second = newJobApplication();
        when(jobApplicationRepository.findAll()).thenReturn(List.of(first, second));

        final List<JobApplication> result = jobApplicationService.readAllJobApplications();

        assertThat(result).containsExactly(first, second);
    }

    @Test
    void testUpdateJobApplicationAppliesFieldsAndSaves() {
        final JobApplication existing = newJobApplication();
        existing.setId(7L);

        final JobApplication updatedData = newJobApplication();
        updatedData.setCompanyName("Globex AG");
        updatedData.setPosition("Senior Backend Developer");
        updatedData.setStatus(Status.INTERVIEW);
        updatedData.setNotes("Zweites Gespräch am 10.10.");

        when(jobApplicationRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(jobApplicationRepository.save(existing)).thenReturn(existing);

        final JobApplication result = jobApplicationService.updateJobApplication(7L, updatedData);

        assertThat(result.getCompanyName()).isEqualTo("Globex AG");
        assertThat(result.getPosition()).isEqualTo("Senior Backend Developer");
        assertThat(result.getStatus()).isEqualTo(Status.INTERVIEW);
        assertThat(result.getNotes()).isEqualTo("Zweites Gespräch am 10.10.");
    }

    @Test
    void testUpdateJobApplicationThrowsWhenNotFound() {
        when(jobApplicationRepository.findById(7L)).thenReturn(Optional.empty());
        final JobApplication updatedData = newJobApplication();

        assertThatThrownBy(() -> jobApplicationService.updateJobApplication(7L, updatedData))
                .isInstanceOf(JobApplicationNotFoundException.class);

        verify(jobApplicationRepository, never()).save(any());
    }

    @Test
    void testDeleteJobApplicationDeletesWhenExists() {
        when(jobApplicationRepository.existsById(3L)).thenReturn(true);

        jobApplicationService.deleteJobApplication(3L);

        verify(jobApplicationRepository, times(1)).deleteById(3L);
    }

    @Test
    void testDeleteJobApplicationThrowsWhenNotExists() {
        when(jobApplicationRepository.existsById(3L)).thenReturn(false);

        assertThatThrownBy(() -> jobApplicationService.deleteJobApplication(3L))
                .isInstanceOf(JobApplicationNotFoundException.class);

        verify(jobApplicationRepository, never()).deleteById(3L);
    }

    private JobApplication newJobApplication() {
        final JobApplication jobApplication = new JobApplication();
        jobApplication.setCompanyName("Acme GmbH");
        jobApplication.setCity("Köln");
        jobApplication.setPosition("Backend Developer");
        jobApplication.setAppliedAt(LocalDate.of(2026, 9, 1));
        jobApplication.setStatus(Status.APPLIED);
        return jobApplication;
    }
}