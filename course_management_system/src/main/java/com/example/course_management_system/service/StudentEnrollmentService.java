package com.example.course_management_system.service;

import com.example.course_management_system.MyExceptionn.MyException;
import com.example.course_management_system.dto.dtoRequest.CourseDropoutRequest;
import com.example.course_management_system.dto.dtoRequest.StudentEnrollmentCreateRequest;
import com.example.course_management_system.dto.dtoResponse.CourseEnrollmentResponse;
import com.example.course_management_system.dto.dtoResponse.PageResponse;
import com.example.course_management_system.dto.dtoResponse.StudentResponse;
import com.example.course_management_system.enums.CourseStatus;
import com.example.course_management_system.model.Course;
import com.example.course_management_system.model.Student;
import com.example.course_management_system.model.StudentEnrollment;
import com.example.course_management_system.repository.CourseRepository;
import com.example.course_management_system.repository.StudentEnrollmentRepository;
import com.example.course_management_system.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentEnrollmentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    @Transactional
    public StudentEnrollment enrollStudent(StudentEnrollmentCreateRequest req){
        //Kiem tra ton tai student va course ko
        Student student = studentRepository.findById(req.getStudentId())
                .orElseThrow(() -> new MyException("No student found with id: " + req.getStudentId()));
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new MyException("No course found with id: " + req.getCourseId()));

        //Neu ton tai thi khoi tao doi tuong va dang ky chi tiet khoa hocj
        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setStudent(student);
        studentEnrollment.setCourse(course);


        return studentEnrollmentRepository.save(studentEnrollment);
    }

    @Transactional
    public CourseEnrollmentResponse enrollResponse(Long studentId, Long courseId){
        //Kiem tra studentId co ton tai
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new MyException("No student found with id: " + studentId));

        //Kiem tra courseId co ton tai
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new MyException("No course found with id: " + courseId));

        //Kiem tra trang thai hoat dong cua khoa hoc
        if(course.getStatus() != CourseStatus.ACTIVE){
            throw new MyException("Course is not active");
        }

        //Kiem tra sinh vien da dang ky khoa hoc truoc do chua
        boolean check = studentEnrollmentRepository.existsByStudentIdAndCourseId(studentId,courseId);
        if(check){
            throw new MyException("Student was exists");
        }

        //Them student vao course
        StudentEnrollment studentEnrollment = StudentEnrollment.builder()
                .student(student)
                .course(course)
                .build();
        StudentEnrollment savedEnrollment = studentEnrollmentRepository.save(studentEnrollment);
        CourseEnrollmentResponse courseEnrollmentResponse =  CourseEnrollmentResponse.builder()
                .studentId(studentId)
                .courseId(courseId)
                .enrollAt(studentEnrollment.getEnrollAt())
                .build();
        return courseEnrollmentResponse;
    }
    //Lay danh sach sinh vien theo id khoa hoc
    public PageResponse<StudentResponse> findStudentByCourseId(
            int page, int size, String sortBy, Sort.Direction direction, Long courseId
    ) {
        if(page < 0){
            page = 0;
        }
        if(size < 0){
            size = 1;
        }
        Sort sort = Sort.unsorted();
        if(direction != null && sortBy != null) {
            sort = Sort.by(direction, sortBy);
        }
        //Kiem tra co id course trong danh sach ko
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new MyException("No course found with id: " + courseId));
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentResponse> list = studentEnrollmentRepository.findStudentByCourseId(courseId,pageable);
        PageResponse<StudentResponse> pageResponse = PageResponse.<StudentResponse>builder()
                .items(list.getContent())
                .page(list.getNumber())
                .size(list.getSize())
                .totalPages(list.getTotalPages())
                .totalItems(list.getTotalElements())
                .isLast(list.isLast())
                .build();
        return pageResponse;
    }
}
