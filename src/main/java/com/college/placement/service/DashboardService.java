package com.college.placement.service;

import com.college.placement.model.ApplicationStatus;
import com.college.placement.model.PlacementDrive;
import com.college.placement.model.Student;
import com.college.placement.repository.ApplicationRepository;
import com.college.placement.repository.CompanyRepository;
import com.college.placement.repository.PlacementDriveRepository;
import com.college.placement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PlacementDriveRepository driveRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", studentRepository.count());
        stats.put("totalCompanies", companyRepository.count());
        stats.put("totalDrives", driveRepository.count());
        stats.put("studentsPlaced", studentRepository.countByIsPlacedTrue());
        stats.put("studentsInProcess", applicationRepository.countByStatus(ApplicationStatus.INTERVIEW) + applicationRepository.countByStatus(ApplicationStatus.SHORTLISTED));
        stats.put("studentsRejected", applicationRepository.countByStatus(ApplicationStatus.REJECTED));
        
        // Alerts/Exceptions
        List<Student> unplacedStudents = studentRepository.findAll().stream()
                .filter(s -> !s.isPlaced())
                .collect(Collectors.toList());
        
        List<PlacementDrive> upcomingDrives = driveRepository.findByStatus("UPCOMING");

        stats.put("unplacedStudentsCount", unplacedStudents.size());
        stats.put("upcomingDrivesCount", upcomingDrives.size());
        stats.put("pendingInterviewsCount", applicationRepository.countByStatus(ApplicationStatus.INTERVIEW));

        return stats;
    }
}
