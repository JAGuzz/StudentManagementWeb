package com.web.studentsApp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AddressDto {
    
    private Long addressId;

    @NotNull(message = "Address Line can not be null or Null")
    private String addressLine;

    @NotNull(message = "City can not be null or Null")
    private String city;

    @NotNull(message = "Zip Postal Code can not be null or Null")
    private String zipPostcode;

    @NotNull(message = "State can not be null or Null")
    private String state;
}
