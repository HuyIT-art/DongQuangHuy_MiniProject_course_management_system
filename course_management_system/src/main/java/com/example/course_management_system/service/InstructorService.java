package com.example.course_management_system.service;

import com.example.course_management_system.dto.dtoRequest.InstructorCreateRequest;
import com.example.course_management_system.model.Instructor;
import com.example.course_management_system.repository.CourseRepository;
import com.example.course_management_system.repository.InstructorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    //Lay instructor theo id
    public Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id).orElse(null);
    }

    //Lay toan boj instructor
    public List<Instructor> findAllInstructors() {
        return instructorRepository.findAll();
    }

    //Tao instructor
    @Transactional
    public Instructor createInstructor(InstructorCreateRequest req) {
        Instructor instructor = Instructor.builder()
                .name(req.getName())
                .email(req.getEmail())
                .build();
        return instructor;
    }
}
