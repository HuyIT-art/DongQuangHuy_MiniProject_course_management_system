package com.example.course_management_system.dto.dtoRequest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseEnrollmentRequest {
    private Long studentId;
}
