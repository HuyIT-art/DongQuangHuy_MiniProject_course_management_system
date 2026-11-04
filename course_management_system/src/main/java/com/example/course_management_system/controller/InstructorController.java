package com.example.course_management_system.controller;

import com.example.course_management_system.dto.dtoResponse.ApiResponse;
import com.example.course_management_system.dto.dtoRequest.InstructorCreateRequest;
import com.example.course_management_system.model.Instructor;
import com.example.course_management_system.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(instructorService.findAllInstructors());
    }
    //Tao mot instructor moi
    @PostMapping
    public ResponseEntity<ApiResponse<Instructor>> addInstructor(@RequestBody InstructorCreateRequest req){
        //Tao instructor
        Instructor instructor = instructorService.createInstructor(req);

        ApiResponse<Instructor> response = ApiResponse.<Instructor>builder()
                .message("Ok")
                .status(true)
                .data(instructor)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
