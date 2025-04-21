package com.web.studentsApp.mapper;

import java.time.LocalDateTime;

import com.web.studentsApp.dto.EmailDto;
import com.web.studentsApp.model.Email;

public class EmailMapper {

    public static EmailDto mapToEmailDto(Email email, EmailDto emailDto) {
        emailDto.setEmail(email.getEmail());
        emailDto.setEmailType(email.getEmailType() != null ? email.getEmailType() : null);
        return emailDto;
    }

    public static Email mapToEmail(EmailDto emailDto, Email email) {
        
        email.setEmail(emailDto.getEmail());
        email.setEmailType(emailDto.getEmailType() != null ? emailDto.getEmailType() : null);
        email.setCreatedOn(LocalDateTime.now());
        return email;
    }

}
