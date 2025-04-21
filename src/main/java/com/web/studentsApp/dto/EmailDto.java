package com.web.studentsApp.dto;

import com.web.studentsApp.model.Email.EmailType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class EmailDto {

    @NotNull(message = "Email can not be null or empty")
    private String email;

    @NotNull(message = "Email Type can not be null or empty")
    private EmailType emailType;
}
