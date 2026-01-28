package com.tw.jay.jayeip_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.jay.jayeip_system.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    public boolean existsByUsername(String username);

    Optional<Employee> findByUsername(String username);
    
}
