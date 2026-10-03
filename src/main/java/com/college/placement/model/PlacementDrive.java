package com.college.placement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "placement_drives")
public class PlacementDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotBlank(message = "Job role is required")
    private String jobRole;

    @NotNull(message = "Minimum CGPA is required")
    private Double minimumCgpa;

    private String status = "UPCOMING"; // UPCOMING, ONGOING, COMPLETED

    @OneToMany(mappedBy = "placementDrive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;

    public PlacementDrive() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getJobRole() { return jobRole; }
    public void setJobRole(String jobRole) { this.jobRole = jobRole; }
    public Double getMinimumCgpa() { return minimumCgpa; }
    public void setMinimumCgpa(Double minimumCgpa) { this.minimumCgpa = minimumCgpa; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
}
