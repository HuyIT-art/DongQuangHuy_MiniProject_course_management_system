package com.example.course_management_system.service;

import com.example.course_management_system.dto.dtoRequest.StudentCreateRequest;
import com.example.course_management_system.dto.dtoResponse.PageResponse;
import com.example.course_management_system.dto.dtoResponse.StudentResponse;
import com.example.course_management_system.model.Student;
import com.example.course_management_system.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    //Lay toan bo danh sach student
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    //Them student
    @Transactional
    public Student createStudent(StudentCreateRequest studentCreateRequest) {
        Student student = Student.builder()
                .name(studentCreateRequest.getName())
                .build();
        return student;
    }

    //Lay danh sach sinh vien dang ky khoa hoc
    public PageResponse<StudentResponse> findAllStudents(int page, int size, String sortBy, Sort.Direction direction) {
        Sort sort = Sort.unsorted();
        if(direction != null && sortBy != null) {
            sort =  Sort.by(direction, sortBy);
        }

        Pageable  pageable = PageRequest.of(page, size, sort);
        Page<StudentResponse> list = studentRepository.findAllStudents(pageable);
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
