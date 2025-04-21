package com.web.studentsApp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.web.studentsApp.dto.EmailDto;
import com.web.studentsApp.dto.StudentDto;
import com.web.studentsApp.exception.ResourceNotFoundException;
import com.web.studentsApp.exception.StudentAlreadyExistsException;
import com.web.studentsApp.mapper.StudentMapper;
import com.web.studentsApp.model.Student;
import com.web.studentsApp.repository.StudentRepository;
import com.web.studentsApp.service.IStudentService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService {

    private StudentRepository studentRepository;

    @Override
    @Transactional
    public void createStudent(StudentDto studentDto) {
        List<String> emailValues = studentDto.getEmails().stream()
                .map(EmailDto::getEmail).collect(Collectors.toList());

        Optional<Student> optionalStudent = studentRepository.findByAnyEmail(emailValues);
        if (optionalStudent.isPresent()) {
            throw new StudentAlreadyExistsException("Student already registered with given email/s.");
        }

        Student student = StudentMapper.mapToStudent(studentDto, new Student());
        student.setCreatedOn(LocalDateTime.now());

        studentRepository.save(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> StudentMapper.mapToStudentDto(student, new StudentDto()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDto> getStudentById(Long id) {
        return Optional.of(studentRepository.findById(id)
                .map(student -> StudentMapper.mapToStudentDto(student, new StudentDto()))
                .orElseThrow(() -> new ResourceNotFoundException("Student", "Id", id + "")));
    }

    @Override
    @Transactional
    public void updateStudent(Long id, StudentDto studentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(id)));

        Student updatedStudent = StudentMapper.updateStudentFromDto(studentDto, student);
        studentRepository.save(updatedStudent);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deletePhone(Long studentId, String phoneNumber) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (student.getPhones().size() <= 1) {
            throw new IllegalStateException("The only remaining phone cannot be deleted");
        }

        student.getPhones().removeIf(phone -> phone.getPhoneNumber().equals(phoneNumber));
        studentRepository.save(student);
    }

    @Transactional
    @Override
    public void deleteEmail(Long studentId, String emailV) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (student.getEmails().size() <= 1) {
            throw new IllegalStateException("The only remaining email cannot be deleted");
        }

        student.getEmails().removeIf(mail -> mail.getEmail().equals(emailV));
        studentRepository.save(student);
    }

    @Transactional
    @Override
    public void deleteAddress(Long studentId, Long addressId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (student.getAddresses().size() <= 1) {
            throw new IllegalStateException("The only remaining address cannot be deleted");
        }

        student.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        studentRepository.save(student);
    }

}
