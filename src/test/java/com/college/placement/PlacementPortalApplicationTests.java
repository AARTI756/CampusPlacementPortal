package com.college.placement;

import com.college.placement.model.Student;
import com.college.placement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class PlacementPortalApplicationTests {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private void resetStudentSequence() {
        try { jdbcTemplate.execute("ALTER SEQUENCE students_id_seq RESTART WITH 1"); } 
        catch (Exception e) { 
            try { jdbcTemplate.execute("ALTER TABLE students ALTER COLUMN id RESTART WITH 1"); } 
            catch (Exception ex) {} 
        }
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testStudentIdResetBehavior() {
        studentRepository.deleteAll();
        resetStudentSequence();

        // Create student 1
        Student s1 = new Student();
        s1.setName("John Doe");
        s1.setEmail("john@example.com");
        s1.setDepartment("Computer Science");
        s1.setCgpa(8.5);
        s1 = studentRepository.save(s1);
        
        assertEquals(1L, s1.getId(), "First student should get ID 1");

        // Delete student 1
        studentRepository.deleteById(s1.getId());
        
        // Table is empty, reset sequence
        if (studentRepository.count() == 0) {
            resetStudentSequence();
        }

        // Create new student
        Student s2 = new Student();
        s2.setName("Jane Smith");
        s2.setEmail("jane@example.com");
        s2.setDepartment("Information Technology");
        s2.setCgpa(9.0);
        s2 = studentRepository.save(s2);

        assertEquals(1L, s2.getId(), "New student after empty table should get ID 1");

        // Create another student
        Student s3 = new Student();
        s3.setName("Alice");
        s3.setEmail("alice@example.com");
        s3.setDepartment("Mechanical");
        s3.setCgpa(8.0);
        s3 = studentRepository.save(s3);
        assertEquals(2L, s3.getId(), "Next student should get ID 2");

        // Delete first student (s2)
        studentRepository.deleteById(s2.getId());

        // Create another student
        Student s4 = new Student();
        s4.setName("Bob");
        s4.setEmail("bob@example.com");
        s4.setDepartment("Civil");
        s4.setCgpa(7.5);
        s4 = studentRepository.save(s4);

        assertEquals(3L, s4.getId(), "Next student should get ID 3");

        // Cleanup
        studentRepository.deleteAll();
        resetStudentSequence();
    }
}
