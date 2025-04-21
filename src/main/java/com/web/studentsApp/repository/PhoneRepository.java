package com.web.studentsApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.studentsApp.model.Phone;

public interface PhoneRepository extends JpaRepository<Phone, String>{

}
