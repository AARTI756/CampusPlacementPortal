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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            model.addAttribute("students", studentRepository.searchStudents(keyword));
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

    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        checkAndResetSequences();
        return "redirect:/students";
    }

    @PostMapping("/students/delete/all")
    public String deleteAllStudents() {
        studentRepository.deleteAll();
        checkAndResetSequences();
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

    @PostMapping("/companies/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        checkAndResetSequences();
        return "redirect:/companies";
    }

    @PostMapping("/companies/delete/all")
    public String deleteAllCompanies() {
        companyRepository.deleteAll();
        checkAndResetSequences();
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

    @PostMapping("/drives/delete/{id}")
    public String deleteDrive(@PathVariable Long id) {
        driveRepository.deleteById(id);
        checkAndResetSequences();
        return "redirect:/drives";
    }

    @PostMapping("/drives/delete/all")
    public String deleteAllDrives() {
        driveRepository.deleteAll();
        checkAndResetSequences();
        return "redirect:/drives";
    }

    @GetMapping("/applications")
    public String applications(@RequestParam(required = false) ApplicationStatus status, Model model) {
        if (status != null) {
            model.addAttribute("applications", applicationRepository.findByStatus(status));
            model.addAttribute("currentStatus", status.name());
        } else {
            model.addAttribute("applications", applicationRepository.findAll());
            model.addAttribute("currentStatus", "ALL");
        }
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

    @PostMapping("/applications/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationRepository.deleteById(id);
        checkAndResetSequences();
        return "redirect:/applications";
    }

    @PostMapping("/applications/delete/all")
    public String deleteAllApplications() {
        applicationRepository.deleteAll();
        checkAndResetSequences();
        return "redirect:/applications";
    }

    private void checkAndResetSequences() {
        resetSequenceIfEmpty("placement_applications", "placement_applications_id_seq", applicationRepository.count());
        resetSequenceIfEmpty("placement_drives", "placement_drives_id_seq", driveRepository.count());
        resetSequenceIfEmpty("companies", "companies_id_seq", companyRepository.count());
        resetSequenceIfEmpty("students", "students_id_seq", studentRepository.count());
    }

    private void resetSequenceIfEmpty(String tableName, String sequenceName, long count) {
        if (count == 0) {
            try {
                // Try PostgreSQL syntax
                jdbcTemplate.execute("ALTER SEQUENCE " + sequenceName + " RESTART WITH 1");
            } catch (Exception e) {
                try {
                    // Try H2 syntax for tests
                    jdbcTemplate.execute("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1");
                } catch (Exception ex) {
                    // Ignore if both fail
                }
            }
        }
    }
}
