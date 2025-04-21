package com.web.studentsApp.mapper;

import java.time.LocalDateTime;

import com.web.studentsApp.dto.PhoneDto;
import com.web.studentsApp.model.Phone;

public class PhoneMapper {

    public static PhoneDto mapToPhoneDto(Phone phone, PhoneDto phoneDto) {
        phoneDto.setPhoneNumber(phone.getPhoneNumber());
        phoneDto.setPhoneType(phone.getPhoneType() != null ? phone.getPhoneType() : null);
        phoneDto.setCountryCode(phone.getCountryCode());
        phoneDto.setAreaCode(phone.getAreaCode());
        return phoneDto;
    }

    public static Phone mapToPhone(PhoneDto phoneDto, Phone phone) {
        phone.setPhoneNumber(phoneDto.getPhoneNumber());
        phone.setPhoneType(phoneDto.getPhoneType() != null ? phoneDto.getPhoneType() : null);
        phone.setCountryCode(phoneDto.getCountryCode());
        phone.setAreaCode(phoneDto.getAreaCode());
        phone.setCreatedOn(LocalDateTime.now());
        return phone;
    }
}
