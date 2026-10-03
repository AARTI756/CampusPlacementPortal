package com.college.placement.repository;

import com.college.placement.model.Application;
import com.college.placement.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);
    List<Application> findByPlacementDriveId(Long driveId);
    List<Application> findByStatus(ApplicationStatus status);
    long countByStatus(ApplicationStatus status);
}
