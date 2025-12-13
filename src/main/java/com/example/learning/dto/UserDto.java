package com.example.learning.dto;

import com.example.learning.validation.ValidUsername;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;

    @NotBlank
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Name must contain only characters"
    )
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid Email")
    private String email;

    /*
    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age must be less than or equal to 60")
    private int age;

    @Positive(message = "Salary must be positive")
    private double salary;

    @Future(message = "Date must be in future")
    private LocalDate startDate;

    @ValidUsername
    private String username;

     */
}
