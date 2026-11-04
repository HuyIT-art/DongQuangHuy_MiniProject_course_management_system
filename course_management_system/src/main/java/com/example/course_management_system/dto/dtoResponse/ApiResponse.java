package com.example.course_management_system.dto.dtoResponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private String message;
    private boolean status;
    private T data;
}
