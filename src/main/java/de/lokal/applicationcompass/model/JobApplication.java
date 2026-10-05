package de.lokal.applicationcompass.model;

import de.lokal.applicationcompass.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "job_application")
@Getter
@Setter
@NoArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(length = 100)
    private String city;

    @Column(nullable = false, length = 200)
    private String position;

    @Column(name = "applied_at", nullable = false)
    private LocalDate appliedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.APPLIED;

    @Column(name = "contact_person", length = 150)
    private String contactPerson;

    @Column(name = "job_url", length = 500)
    private String jobUrl;

    @Column(columnDefinition = "text")
    private String notes;
}