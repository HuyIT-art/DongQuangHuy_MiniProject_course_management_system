package com.example.course_management_system.controller;

import com.example.course_management_system.dto.dtoRequest.CourseEnrollmentRequest;
import com.example.course_management_system.dto.dtoResponse.*;
import com.example.course_management_system.dto.dtoRequest.CourseCreateRequest;
import com.example.course_management_system.dto.dtoRequest.CourseUpdateRequest;
import com.example.course_management_system.enums.CourseStatus;
import com.example.course_management_system.model.Course;
import com.example.course_management_system.service.CourseService;
import com.example.course_management_system.service.StudentEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final StudentEnrollmentService studentEnrollmentService;

    //Lấy danh sách các khóa học
    @GetMapping("/list")
    public ResponseEntity<?> findAllCourse(){
        List<CourseResponse> list = courseService.findAllCourse();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //Them course moi
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addCourse(@RequestBody CourseCreateRequest courseCreateRepuest){
        courseService.createCourse(courseCreateRepuest);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Course Created Successfully")
                .status(true)
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    //Cap nhat course
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateCourse(@RequestBody CourseUpdateRequest courseUpdateRequest, @PathVariable Long id){
        //Thuc hien cap nhat
        courseService.updateCourse(id, courseUpdateRequest);

        // neu ko loi gui thong bao
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Course Updated Successfully")
                .status(true)
                .data(null)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourse() {
//        ApiResponse<List<CourseResponse>> response = ApiResponse.<List<CourseResponse>>builder()
//                .message("OK")
//                .status(true)
//                .data(courseService.getAllCourses()) // Truyền biến courses vào đây
//                .build();
//
//        // 3. Trả về Response
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
    @PostMapping("/{courseId}/enrollments")
    //Them student vao course
    public ResponseEntity<CourseEnrollmentResponse>  createCourseEnrollment(@PathVariable Long courseId,
                                                                            @RequestBody CourseEnrollmentRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentEnrollmentService.enrollResponse(request.getStudentId(), courseId));
    }

    //Lay tat ca cac danh sach khoa hoc da sap xep
    @GetMapping()
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> getAllCourse(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam Sort.Direction direction
    ){
        PageResponse<CourseResponse> list = courseService.getPagedCourses(page, size, sortBy, direction);
        ApiResponse<PageResponse<CourseResponse>> response = ApiResponse.<PageResponse<CourseResponse>>builder()
                .message("Course List")
                .status(true)
                .data(list)
                .build();

        return ResponseEntity.ok(response);
    }

    //Lay thong tin danh sach khoa hoc theo trang thai
    @GetMapping("/by-statusV1")
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> getCoursesByStatus(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam Sort.Direction direction,
            @RequestParam(defaultValue = "ACTIVE") CourseStatus status
    ){
        PageResponse<CourseResponse> pageResponse = courseService.getPagedCoursesByStatus(page, size, sortBy, direction, status);
        ApiResponse<PageResponse<CourseResponse>> response = ApiResponse.<PageResponse<CourseResponse>>builder()
                .message("Lấy danh sách theo trạng thái thành công!")
                .status(true)
                .data(pageResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-statusV2")
    public ResponseEntity<ApiResponse<PageResponse<CourseResponseV2>>> getCoursesByStatusV2(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam Sort.Direction direction,
            @RequestParam(defaultValue = "ACTIVE") CourseStatus status
    ){
        PageResponse<CourseResponseV2> pageResponse = courseService.getPagedCoursesByStatusV2(page, size, sortBy, direction, status);
        ApiResponse<PageResponse<CourseResponseV2>> response = ApiResponse.<PageResponse<CourseResponseV2>>builder()
                .message("Lấy danh sách theo trạng thái thành công!")
                .status(true)
                .data(pageResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/conditions")
    public ResponseEntity<ApiResponse<PageResponse<CourseResponseV2>>> getCoursesByConditions(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size,
         @RequestParam(defaultValue = "id") String sortBy,
         @RequestParam(required = false) Sort.Direction direction,
         @RequestParam(required = false) CourseStatus status,
         @RequestParam(required = false) String keyword
    ){
        PageResponse<CourseResponseV2> pageResponse = courseService.getPagedCoursesByConditions(page, size, sortBy, direction, status, keyword);
        ApiResponse<PageResponse<CourseResponseV2>> response = ApiResponse.<PageResponse<CourseResponseV2>>builder()
                .message("Lấy danh sách theo trạng thái thành công!")
                .status(true)
                .data(pageResponse)
                .build();
        return ResponseEntity.ok(response);
    }

}
