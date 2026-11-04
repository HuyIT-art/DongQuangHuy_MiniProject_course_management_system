package com.example.course_management_system.dto.dtoRequest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CourseDropoutRequest {
    private Long studentId;
}
