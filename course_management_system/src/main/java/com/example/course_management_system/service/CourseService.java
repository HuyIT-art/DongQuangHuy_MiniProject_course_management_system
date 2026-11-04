package com.example.course_management_system.service;

import com.example.course_management_system.MyExceptionn.MyException;
import com.example.course_management_system.dto.dtoRequest.CourseCreateRequest;
import com.example.course_management_system.dto.dtoResponse.CourseResponseV2;
import com.example.course_management_system.dto.dtoResponse.InstructorResponse;
import com.example.course_management_system.dto.dtoResponse.CourseResponse;
import com.example.course_management_system.dto.dtoRequest.CourseUpdateRequest;
import com.example.course_management_system.dto.dtoResponse.PageResponse;
import com.example.course_management_system.enums.CourseStatus;
import com.example.course_management_system.model.Course;
import com.example.course_management_system.model.Instructor;
import com.example.course_management_system.repository.CourseRepository;
import com.example.course_management_system.repository.InstructorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    //Lay danh sach cac khoa hoc
    public List<CourseResponse> findAllCourse(){
        List<Course> list = courseRepository.findAll();
        List<CourseResponse> listResponse = list.stream()
                .map(c -> new CourseResponse(
                        c.getId(),c.getTitle(),c.getStatus(),new InstructorResponse(c.getInstructor().getId(),c.getInstructor().getName())))
                .toList();
        return listResponse;
    }

    public Course createCourse(CourseCreateRequest req){
        //Tim instructor có id giôngs với yêu cầu để thêm vào course
        Instructor instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new MyException("Khong co id cua Instructor"));
        //Tạo courser mới, thay vì thêm ở phần body của postman thì ta thêm bằng CourseCreateRequest
        Course course = new Course();
        course.setStatus(req.getStatus());
        course.setTitle(req.getTitle());
        course.setInstructor(instructor);

        //Thêm đối tượng vào Course
        return courseRepository.save(course);
    }

@Transactional
    public Course updateCourse(Long id, CourseUpdateRequest req){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new MyException("Khong co id cua Course"));
        Instructor instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new MyException("Khong co id cua Instructor"));
        course.setTitle(req.getTitle());
        course.setStatus(req.getStatus());
        course.setInstructor(instructor);
        return courseRepository.save(course);

        }
    @Transactional()
    public List<CourseResponse> getAllCourses() {
        // Sử dụng hàm đã tối ưu JOIN FETCH ở Repo
        return courseRepository.findAll().stream()
                .map(c -> new CourseResponse(
                        c.getId(),
                        c.getTitle(),
                        c.getStatus(),
                        c.getInstructor() != null
                                ? new InstructorResponse(
                                c.getInstructor().getId(),
                                c.getInstructor().getName())
                                : null
                ))
                .toList();
    }

    //Lay danh sách cac khoa hoc
    public PageResponse<CourseResponse> getPagedCourses(int page, int size, String sortBy, Sort.Direction direction){
        if(page < 0){
            page = 0;
        }
        if(sortBy == null){
            sortBy = "id";
        }
        if (direction == null) {
            direction = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<CourseResponse> list = courseRepository.findAllCourse(pageable);
        PageResponse<CourseResponse> pageResponse = PageResponse.<CourseResponse>builder()
                .items(list.getContent())
                .page(list.getNumber())
                .size(list.getSize())
                .totalItems(list.getTotalElements())
                .totalPages(list.getTotalPages())
                .isLast(list.isLast())
                .build();
        return pageResponse;
    }
    //Lay danh sach khoa hoc dua vao status
    public PageResponse<CourseResponse> getPagedCoursesByStatus(int page, int size, String sortBy,
                                                                Sort.Direction direction, CourseStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Course> list = courseRepository.findAllByStatus(status, pageable);
        Page<CourseResponse> list2 = list.map(s -> new CourseResponse(s.getId(), s.getTitle(), s.getStatus()
                , new InstructorResponse(s.getInstructor().getId(), s.getInstructor().getName())));
        PageResponse<CourseResponse> pageResponse = PageResponse.<CourseResponse>builder()
                .items(list2.getContent())
                .page(list2.getNumber())
                .size(list2.getSize())
                .totalItems(list2.getTotalElements())
                .totalPages(list2.getTotalPages())
                .isLast(list2.isLast())
                .build();
        return pageResponse;
    }

    //Lay danh sach khoa hoc dua vao status ban V2
    public PageResponse<CourseResponseV2> getPagedCoursesByStatusV2(int page, int size, String sortBy,
                                                                Sort.Direction direction, CourseStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<CourseResponseV2> list = courseRepository.findAllByStatusV2(status,pageable);
        PageResponse<CourseResponseV2> pageResponse = PageResponse.<CourseResponseV2>builder()
                .items(list.getContent())
                .page(list.getNumber())
                .size(list.getSize())
                .totalItems(list.getTotalElements())
                .totalPages(list.getTotalPages())
                .isLast(list.isLast())
                .build();
        return pageResponse;
    }

    //Lay danh sach khoa hoc tren nhieu dieu kien
    public PageResponse<CourseResponseV2> getPagedCoursesByConditions(int page, int size, String sortBy, Sort.Direction direction, CourseStatus status, String keyword) {
        if(page < 0){
            page = 0;
        }
        if(sortBy == null){
            sortBy = "id";
        }
        Sort sort = Sort.unsorted();
        if (direction != null && sortBy != null) {
            sort = Sort.by(direction, sortBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CourseResponseV2> list = courseRepository.findAllCoursesByManyCoditions(status, keyword, pageable);
        PageResponse<CourseResponseV2> pageResponse = PageResponse.<CourseResponseV2>builder()
                .items(list.getContent())
                .page(list.getNumber())
                .size(list.getSize())
                .totalItems(list.getTotalElements())
                .totalPages(list.getTotalPages())
                .isLast(list.isLast())
                .build();
        return pageResponse;

    }
}
