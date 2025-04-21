package com.web.studentsApp.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.web.studentsApp.dto.AddressDto;
import com.web.studentsApp.dto.EmailDto;
import com.web.studentsApp.dto.PhoneDto;
import com.web.studentsApp.dto.StudentDto;
import com.web.studentsApp.model.Address;
import com.web.studentsApp.model.Email;
import com.web.studentsApp.model.Phone;
import com.web.studentsApp.model.Student;

public class StudentMapper {

        public static Student updateStudentFromDto(StudentDto dto, Student student) {
                student.setFirstName(dto.getFirstName());
                student.setLastName(dto.getLastName());
                student.setGender(dto.getGender());
                student.setUpdatedOn(LocalDateTime.now());
        
                // --- Emails ---
                student.getEmails().clear(); // Limpiar la colección
                for (EmailDto emailDto : dto.getEmails()) {
                    if (emailDto.getEmail() != null && !emailDto.getEmail().isBlank()) {
                        Email email = new Email();
                        email.setEmail(emailDto.getEmail());
                        email.setEmailType(emailDto.getEmailType());
                        email.setStudent(student);
                        email.setUpdatedOn(LocalDateTime.now());
                        student.getEmails().add(email);
                    }
                }
        
                // --- Phones ---
                student.getPhones().clear();
                for (PhoneDto phoneDto : dto.getPhones()) {
                    if (phoneDto.getPhoneNumber() != null && !phoneDto.getPhoneNumber().isBlank()) {
                        Phone phone = new Phone();
                        phone.setPhoneNumber(phoneDto.getPhoneNumber());
                        phone.setPhoneType(phoneDto.getPhoneType());
                        phone.setCountryCode(phoneDto.getCountryCode());
                        phone.setAreaCode(phoneDto.getAreaCode());;
                        phone.setStudent(student);
                        phone.setUpdatedOn(LocalDateTime.now());
                        student.getPhones().add(phone);
                    }
                }
        
                // --- Addresses ---
                student.getAddresses().clear();
                for (AddressDto addressDto : dto.getAddresses()) {
                    if (addressDto.getAddressLine() != null && !addressDto.getAddressLine().isBlank()) {
                        Address address = new Address();
                        address.setAddressId(addressDto.getAddressId());
                        address.setAddressLine(addressDto.getAddressLine());
                        address.setCity(addressDto.getCity());
                        address.setState(addressDto.getState());
                        address.setZipPostcode(addressDto.getZipPostcode());;
                        address.setStudent(student);
                        address.setUpdatedOn(LocalDateTime.now());
                        student.getAddresses().add(address);
                    }
                }

                return student;
            }

    public static StudentDto mapToStudentDto(Student student, StudentDto studentDto) {
        studentDto.setStudentId(student.getStudentId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setMiddleName(student.getMiddleName());
        studentDto.setLastName(student.getLastName());
        studentDto.setGender(student.getGender());

        // Mapeo de emails
        studentDto.setEmails(mapEntityListToDtoList(
                student.getEmails(),
                EmailMapper::mapToEmailDto,
                EmailDto::new));

        // Mapeo de phones
        studentDto.setPhones(mapEntityListToDtoList(
                student.getPhones(),
                PhoneMapper::mapToPhoneDto,
                PhoneDto::new));

        // Mapeo de addresses
        studentDto.setAddresses(mapEntityListToDtoList(
                student.getAddresses(),
                AddressMapper::mapToAddressDto,
                AddressDto::new));

        return studentDto;
    }

    public static Student mapToStudent(StudentDto studentDto, Student student) {
        student.setFirstName(studentDto.getFirstName());
        student.setMiddleName(studentDto.getMiddleName());
        student.setLastName(studentDto.getLastName());
        student.setGender(studentDto.getGender());

        // Mapeo de Emails
        List<Email> emails = mapDtoListToEntityList(
                studentDto.getEmails(),
                EmailMapper::mapToEmail,
                Email::new);
        emails.forEach(email -> email.setStudent(student));
        student.setEmails(emails);

        // Mapeo de Phones
        List<Phone> phones = mapDtoListToEntityList(
                studentDto.getPhones(),
                PhoneMapper::mapToPhone,
                Phone::new);
        phones.forEach(phone -> phone.setStudent(student));
        student.setPhones(phones);

        // Mapeo de Addresses
        List<Address> addresses = mapDtoListToEntityList(
                studentDto.getAddresses(),
                AddressMapper::mapToAddress,
                Address::new);
        addresses.forEach(address -> address.setStudent(student));
        student.setAddresses(addresses);

        return student;
    }

    // Metodo generico para mapear objetos DTO a Entity
    private static <E, D> List<D> mapEntityListToDtoList(
            List<E> entityList,
            BiFunction<E, D, D> mapperFunction,
            Supplier<D> dtoSupplier) {

        return Optional.ofNullable(entityList)
                .orElse(Collections.emptyList())
                .stream()
                .map(entity -> mapperFunction.apply(entity, dtoSupplier.get()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Metodo generico para mapear objetos Entity a DTO
    private static <D, E> List<E> mapDtoListToEntityList(
            List<D> dtoList,
            BiFunction<D, E, E> mapperFunction,
            Supplier<E> entitySupplier) {

        return Optional.ofNullable(dtoList)
                .orElse(Collections.emptyList())
                .stream()
                .map(dto -> mapperFunction.apply(dto, entitySupplier.get()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
