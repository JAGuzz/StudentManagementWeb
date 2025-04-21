package com.web.studentsApp.dto;

import java.util.ArrayList;
import java.util.List;

import com.web.studentsApp.model.Student.Gender;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentDto {

    private Long studentId;

    @NotNull(message = "Lastname can not be null or empty")
    private String lastName;

    private String middleName;

    @NotNull(message = "Firstname can not be null or empty")
    private String firstName;

    @NotNull(message = "Gender can not be null or empty")
    private Gender gender;

    @NotEmpty(message = "Emails can not be null or empty")
    private List<EmailDto> emails= new ArrayList<>();

    @NotEmpty(message = "Phones can not be null or empty")
    private List<PhoneDto> phones= new ArrayList<>();

    @NotEmpty(message = "Addresses can not be null or empty")
    private List<AddressDto> addresses= new ArrayList<>();
}
