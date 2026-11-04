package com.example.course_management_system.controller;

import com.example.course_management_system.dto.dtoResponse.ApiResponse;
import com.example.course_management_system.dto.dtoRequest.StudentEnrollmentCreateRequest;
import com.example.course_management_system.dto.dtoResponse.CourseEnrollmentResponse;
import com.example.course_management_system.dto.dtoResponse.PageResponse;
import com.example.course_management_system.dto.dtoResponse.StudentResponse;
import com.example.course_management_system.service.CourseService;
import com.example.course_management_system.service.StudentEnrollmentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class StudentEnrollmentController {
    private final StudentEnrollmentService studentEnrollmentService;
    private final CourseService courseService;

    //them studentenrollment
    public ResponseEntity<ApiResponse<Void>> createStudentEnrollment(StudentEnrollmentCreateRequest req) {
        studentEnrollmentService.enrollStudent(req);

        ApiResponse<Void>  response = ApiResponse.<Void>builder()
                .message("StudentEnrollment Created Successfull")
                .status(true)
                .data(null).build();
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //Lay danh sach sinh vien theo id khoa hoc
    @GetMapping("/by-courseId")
    public ResponseEntity<ApiResponse<PageResponse<StudentResponse>>> getStudentByCourseId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(defaultValue = "1") Long courseId
    ) {
        PageResponse<StudentResponse> pageResponse = studentEnrollmentService.findStudentByCourseId(page, size, sortBy, direction, courseId);
        ApiResponse<PageResponse<StudentResponse>> response = ApiResponse.<PageResponse<StudentResponse>>builder()
                .message("Lay danh sach thanh cong!")
                .status(true)
                .data(pageResponse)
                .build();
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
