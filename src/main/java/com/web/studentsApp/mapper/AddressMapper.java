package com.web.studentsApp.mapper;

import java.time.LocalDateTime;

import com.web.studentsApp.dto.AddressDto;
import com.web.studentsApp.model.Address;

public class AddressMapper {

    public static AddressDto mapToAddressDto(Address address, AddressDto addressDto){
        addressDto.setAddressId(address.getAddressId());
        addressDto.setAddressLine(address.getAddressLine());
        addressDto.setZipPostcode(address.getZipPostcode());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        
        return addressDto;
    }

    public static Address mapToAddress(AddressDto addressDto , Address address){
        
        address.setAddressLine(addressDto.getAddressLine());
        address.setZipPostcode(addressDto.getZipPostcode());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setCreatedOn(LocalDateTime.now());
        
        return address;
    }

}
