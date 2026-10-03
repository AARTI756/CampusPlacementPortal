package com.college.placement.controller;

import com.college.placement.model.Application;
import com.college.placement.model.ApplicationStatus;
import com.college.placement.model.Company;
import com.college.placement.model.PlacementDrive;
import com.college.placement.model.Student;
import com.college.placement.repository.ApplicationRepository;
import com.college.placement.repository.CompanyRepository;
import com.college.placement.repository.PlacementDriveRepository;
import com.college.placement.repository.StudentRepository;
import com.college.placement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PlacementDriveRepository driveRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("stats", dashboardService.getDashboardStats());
        return "dashboard";
    }

    @GetMapping("/students")
    public String students(Model model, @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("students", studentRepository.findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(keyword, keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("students", studentRepository.findAll());
        }
        return "students";
    }

    @GetMapping("/students/new")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/companies")
    public String companies(Model model, @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("companies", companyRepository.findByNameContainingIgnoreCase(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("companies", companyRepository.findAll());
        }
        return "companies";
    }

    @GetMapping("/companies/new")
    public String newCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "company_form";
    }

    @PostMapping("/companies")
    public String saveCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/drives")
    public String drives(Model model) {
        model.addAttribute("drives", driveRepository.findAll());
        return "drives";
    }

    @GetMapping("/drives/new")
    public String newDriveForm(Model model) {
        model.addAttribute("drive", new PlacementDrive());
        model.addAttribute("companies", companyRepository.findAll());
        return "drive_form";
    }

    @PostMapping("/drives")
    public String saveDrive(@ModelAttribute PlacementDrive drive) {
        driveRepository.save(drive);
        return "redirect:/drives";
    }

    @GetMapping("/applications")
    public String applications(Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        return "applications";
    }

    @GetMapping("/applications/new")
    public String newApplicationForm(Model model) {
        model.addAttribute("application", new Application());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("drives", driveRepository.findAll());
        return "application_form";
    }

    @PostMapping("/applications")
    public String saveApplication(@ModelAttribute Application application) {
        applicationRepository.save(application);
        return "redirect:/applications";
    }

    @PostMapping("/applications/{id}/status")
    public String updateApplicationStatus(@PathVariable Long id, @RequestParam ApplicationStatus status) {
        Application application = applicationRepository.findById(id).orElseThrow();
        application.setStatus(status);
        if (status == ApplicationStatus.SELECTED) {
            Student student = application.getStudent();
            student.setPlaced(true);
            studentRepository.save(student);
        }
        applicationRepository.save(application);
        return "redirect:/applications";
    }
}
