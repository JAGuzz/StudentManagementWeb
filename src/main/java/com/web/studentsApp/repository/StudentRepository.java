package com.web.studentsApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.studentsApp.model.Student;

import java.util.List;
import java.util.Optional;



public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query("SELECT s FROM Student s JOIN s.emails e WHERE e.email IN :emails")
    Optional<Student> findByAnyEmail(@Param("emails") List<String> emails); 

}
