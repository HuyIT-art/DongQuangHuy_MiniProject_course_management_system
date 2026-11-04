package com.example.course_management_system.repository;

import com.example.course_management_system.dto.dtoResponse.CourseResponse;
import com.example.course_management_system.dto.dtoResponse.CourseResponseV2;
import com.example.course_management_system.enums.CourseStatus;
import com.example.course_management_system.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    public boolean existsByIdAndStatus(Long id, CourseStatus status);

    //Lấy danh sách các khóa họa phân trang và sắp xếp
    @Query("Select new  com.example.course_management_system.dto.dtoResponse.CourseResponse(c.id, c.title, c.status, " +
            "new  com.example.course_management_system.dto.dtoResponse.InstructorResponse(c.instructor.id,c.instructor.name))" +
            "from Course c")
    Page<CourseResponse> findAllCourse(Pageable pageable);

    //Lấy danh sách khóa học dựa trên status
    @Query("Select c from Course c " +
            "where c.status = :status")
    Page<Course> findAllByStatus(@Param("status") CourseStatus status, Pageable pageable);

    //Lay danh sach khoa hoc
    @Query("Select new com.example.course_management_system.dto.dtoResponse.CourseResponseV2(" +
            "c.id," +
            "c.title," +
            " c.status) " +
            "from Course c " +
            "where c.status = :status")
    Page<CourseResponseV2> findAllByStatusV2(@Param("status") CourseStatus status, Pageable pageable);

    //Lay dánh sach khoa hoc tren nhieu dieu kien
    @Query("Select new com.example.course_management_system.dto.dtoResponse.CourseResponseV2(" +
            "c.id," +
            "c.title," +
            " c.status) " +
            "from Course c " +
            "where (:status is null or c.status = :status) " +
            " and (:keyword is null or c.title ILIKE concat('%',:keyword,'%'))")
    Page<CourseResponseV2> findAllCoursesByManyCoditions(@Param("status") CourseStatus status,@Param("keyword") String title, Pageable pageable);
}

