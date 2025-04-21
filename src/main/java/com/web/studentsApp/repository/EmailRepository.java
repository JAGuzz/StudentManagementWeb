package com.web.studentsApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.studentsApp.model.Email;

public interface EmailRepository extends JpaRepository<Email, String>{

}
