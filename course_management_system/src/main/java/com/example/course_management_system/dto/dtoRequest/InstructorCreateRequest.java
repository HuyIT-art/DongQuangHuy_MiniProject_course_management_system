package com.example.course_management_system.dto.dtoRequest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorCreateRequest {
    private  String name;
    private String email;
}
