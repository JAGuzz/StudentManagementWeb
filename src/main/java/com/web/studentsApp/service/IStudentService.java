package com.web.studentsApp.service;

import java.util.List;
import java.util.Optional;

import com.web.studentsApp.dto.StudentDto;

public interface IStudentService {

    public void createStudent(StudentDto studentDto);

    public List<StudentDto> getAllStudents();

    public Optional<StudentDto> getStudentById(Long id);

    public void updateStudent(Long id, StudentDto studentDto);

    public void deleteStudentById(Long id);

    public void deletePhone(Long studentId, String phoneNumber);

    public void deleteEmail(Long studentId, String emailV);

    public void deleteAddress(Long studentId, Long addressId);

}
