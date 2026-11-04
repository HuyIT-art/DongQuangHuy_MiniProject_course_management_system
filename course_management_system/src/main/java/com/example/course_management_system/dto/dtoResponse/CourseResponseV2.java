package com.example.course_management_system.dto.dtoResponse;

import com.example.course_management_system.enums.CourseStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponseV2 {
    private Long id;
    private String title;
    private CourseStatus status;
}
