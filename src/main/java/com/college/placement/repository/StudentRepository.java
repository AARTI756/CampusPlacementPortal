package com.college.placement.repository;

import com.college.placement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String name, String department);
    long countByIsPlacedTrue();
    long countByIsPlacedFalse();
}
