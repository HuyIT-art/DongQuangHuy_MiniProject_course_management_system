package com.example.course_management_system.dto.dtoRequest;

import com.example.course_management_system.enums.CourseStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CourseUpdateRequest {
    private String title;
    private CourseStatus status;
    private Long instructorId;
}
