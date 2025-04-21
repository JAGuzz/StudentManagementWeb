package com.web.studentsApp.dto;

import com.web.studentsApp.model.Phone.PhoneType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class PhoneDto {

    @NotNull(message = "Phone Number can not be null or empty")
    private String phoneNumber;

    @NotNull(message = "Phone Type can not be null or empty")
    private PhoneType phoneType;

    @NotNull(message = "Country Code can not be null or empty")
    private String countryCode;

    @NotNull(message = "Area Code can not be null or empty")
    private String areaCode;
}
