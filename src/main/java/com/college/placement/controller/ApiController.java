package com.college.placement.controller;

import com.college.placement.model.Application;
import com.college.placement.model.Company;
import com.college.placement.model.PlacementDrive;
import com.college.placement.model.Student;
import com.college.placement.repository.ApplicationRepository;
import com.college.placement.repository.CompanyRepository;
import com.college.placement.repository.PlacementDriveRepository;
import com.college.placement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PlacementDriveRepository driveRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/companies")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @PostMapping("/companies")
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @GetMapping("/drives")
    public List<PlacementDrive> getAllDrives() {
        return driveRepository.findAll();
    }

    @PostMapping("/drives")
    public PlacementDrive createDrive(@RequestBody PlacementDrive drive) {
        return driveRepository.save(drive);
    }

    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
