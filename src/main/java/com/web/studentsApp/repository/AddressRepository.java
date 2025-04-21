package com.web.studentsApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.studentsApp.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {



}
