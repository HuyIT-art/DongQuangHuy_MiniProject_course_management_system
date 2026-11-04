package com.example.course_management_system.dto.dtoResponse;

import com.example.course_management_system.enums.CourseStatus;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private CourseStatus status;
    private InstructorResponse instructor;

    public CourseResponse(Long id, String title, CourseStatus status, InstructorResponse instructor) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.instructor = instructor;
    }

    public CourseResponse(Long id, String title, CourseStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }
}
