package com.example.course_management_system.controller;

import com.example.course_management_system.dto.dtoResponse.ApiResponse;
import com.example.course_management_system.dto.dtoRequest.StudentCreateRequest;
import com.example.course_management_system.dto.dtoResponse.PageResponse;
import com.example.course_management_system.dto.dtoResponse.StudentResponse;
import com.example.course_management_system.model.Student;
import com.example.course_management_system.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    //Lay toan bo danh sach student
//    @GetMapping
//    public List<Student> findAll() {
//        return studentService.findAllStudents();
//    }

    //Them student
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody StudentCreateRequest req) {
        studentService.createStudent(req);
        ApiResponse<Void>  response = ApiResponse.<Void>builder()
                .message("Student Created Successfull")
                .status(true)
                .data(null).build();
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    //Lay danh sach toan bo sinh vien
    @GetMapping()
    public ResponseEntity<ApiResponse<PageResponse<StudentResponse>>> findAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction direction
    ) {
        PageResponse<StudentResponse> pageResponse = studentService.findAllStudents(page, size, sortBy, direction);
        ApiResponse<PageResponse<StudentResponse>> response = ApiResponse.<PageResponse<StudentResponse>>builder()
                .message("Lay danh sach student thanh cong!")
                .status(true)
                .data(pageResponse)
                .build();
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}

