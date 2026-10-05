package de.lokal.applicationcompass.controller;

import tools.jackson.databind.ObjectMapper;
import de.lokal.applicationcompass.dto.CreateJobApplicationRequest;
import de.lokal.applicationcompass.dto.UpdateJobApplicationRequest;
import de.lokal.applicationcompass.enums.Status;
import de.lokal.applicationcompass.exceptions.JobApplicationNotFoundException;
import de.lokal.applicationcompass.model.JobApplication;
import de.lokal.applicationcompass.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobApplicationController.class)
class JobApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JobApplicationService jobApplicationService;

    @Test
    void testCreateReturnsCreatedJobApplication() throws Exception {
        final CreateJobApplicationRequest request = new CreateJobApplicationRequest(
                "Acme GmbH", "Köln", "Backend Developer", LocalDate.of(2026, 9, 1), "Frau Müller", null, null);

        when(jobApplicationService.createJobApplication(any(JobApplication.class)))
                .thenAnswer(invocation -> {
                    final JobApplication jobApplication = invocation.getArgument(0);
                    jobApplication.setId(100L);
                    return jobApplication;
                });

        mockMvc.perform(post("/api/job-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.companyName").value("Acme GmbH"))
                .andExpect(jsonPath("$.city").value("Köln"));
    }

    @Test
    void testCreateReturnsBadRequestWhenPositionIsBlank() throws Exception {
        final CreateJobApplicationRequest request = new CreateJobApplicationRequest(
                "Acme GmbH", "Köln", "", LocalDate.of(2026, 9, 1), null, null, null);

        mockMvc.perform(post("/api/job-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateReturnsBadRequestWhenCompanyNameIsBlank() throws Exception {
        final CreateJobApplicationRequest request = new CreateJobApplicationRequest(
                "", "Köln", "Backend Developer", LocalDate.of(2026, 9, 1), null, null, null);

        mockMvc.perform(post("/api/job-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetByIdReturnsJobApplicationWhenFound() throws Exception {
        when(jobApplicationService.readJobApplicationById(100L)).thenReturn(newJobApplication());

        mockMvc.perform(get("/api/job-applications/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position").value("Backend Developer"));
    }

    @Test
    void testGetByIdReturnsNotFoundWhenMissing() throws Exception {
        when(jobApplicationService.readJobApplicationById(999L))
                .thenThrow(new JobApplicationNotFoundException(JobApplicationService.NOT_FOUND_WITH_ID + 999L));

        mockMvc.perform(get("/api/job-applications/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllReturnsAllJobApplications() throws Exception {
        when(jobApplicationService.readAllJobApplications()).thenReturn(List.of(newJobApplication()));

        mockMvc.perform(get("/api/job-applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testUpdateReturnsUpdatedJobApplication() throws Exception {
        final UpdateJobApplicationRequest request = new UpdateJobApplicationRequest(
                "Acme GmbH", "Köln", "Senior Backend Developer", LocalDate.of(2026, 9, 1),
                Status.INTERVIEW, null, null, null);

        final JobApplication updated = newJobApplication();
        updated.setPosition("Senior Backend Developer");
        updated.setStatus(Status.INTERVIEW);

        when(jobApplicationService.updateJobApplication(any(Long.class), any(JobApplication.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/job-applications/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("INTERVIEW"));
    }

    @Test
    void testDeleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/job-applications/100"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteReturnsNotFoundWhenMissing() throws Exception {
        doThrow(new JobApplicationNotFoundException(JobApplicationService.NOT_FOUND_WITH_ID + 999L))
                .when(jobApplicationService).deleteJobApplication(999L);

        mockMvc.perform(delete("/api/job-applications/999"))
                .andExpect(status().isNotFound());
    }

    private JobApplication newJobApplication() {
        final JobApplication jobApplication = new JobApplication();
        jobApplication.setId(100L);
        jobApplication.setCompanyName("Acme GmbH");
        jobApplication.setCity("Köln");
        jobApplication.setPosition("Backend Developer");
        jobApplication.setAppliedAt(LocalDate.of(2026, 9, 1));
        jobApplication.setStatus(Status.APPLIED);
        return jobApplication;
    }
}