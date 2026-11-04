package com.example.course_management_system.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder
@Table(name = "student_enrollments")
public class StudentEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    private LocalDateTime enrollAt;
    @PrePersist
    public void onCreate(){
        this.enrollAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;



}
