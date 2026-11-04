package com.example.course_management_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "instructors")
@Builder
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( length = 100, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String email;

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;

}
