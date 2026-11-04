package com.example.course_management_system.dto.dtoRequest;

import com.example.course_management_system.enums.CourseStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreateRequest {
    private String title;
    private CourseStatus status;
    private Long instructorId;
}
