package com.example.course_management_system.repository;

import com.example.course_management_system.dto.dtoResponse.StudentResponse;
import com.example.course_management_system.model.StudentEnrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    public boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    //Kiem tra co ton tai id khoa hoc khong

    //Tra ra ca sinh vien trong khoa hoc bat ky
    @Query("select new com.example.course_management_system.dto.dtoResponse.StudentResponse(" +
            "se.student.id,se.student.name,se.student.email) " +
            "from StudentEnrollment se " +
            "where se.course.id = :id")
    Page<StudentResponse> findStudentByCourseId(@Param("id") Long courseId, Pageable pageable);
}
