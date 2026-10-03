package com.college.placement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "placement_applications", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "drive_id"})
})
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_id", nullable = false)
    private PlacementDrive placementDrive;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    private LocalDate applicationDate = LocalDate.now();

    public Application() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public PlacementDrive getPlacementDrive() { return placementDrive; }
    public void setPlacementDrive(PlacementDrive placementDrive) { this.placementDrive = placementDrive; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
}
