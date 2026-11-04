package com.example.course_management_system.repository;

import com.example.course_management_system.dto.dtoResponse.CourseResponse;
import com.example.course_management_system.dto.dtoResponse.PageResponse;
import com.example.course_management_system.dto.dtoResponse.StudentResponse;
import com.example.course_management_system.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    //Tra ra danh sach studentResponse
    @Query("Select new com.example.course_management_system.dto.dtoResponse.StudentResponse(" +
            "s.id,s.name,s.email) " +
            "from Student s")
    Page<StudentResponse> findAllStudents(Pageable pageable);


}
