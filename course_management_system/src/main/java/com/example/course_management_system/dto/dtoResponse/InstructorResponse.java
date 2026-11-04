package com.example.course_management_system.dto.dtoResponse;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InstructorResponse {
    private Long id;
    private String name;
}
