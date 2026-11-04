package com.example.course_management_system.dto.dtoRequest;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollmentCreateRequest {
    private Long  studentId;
    private Long courseId;
}
