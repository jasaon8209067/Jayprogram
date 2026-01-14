package com.tw.jay.jayeip_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tw.jay.jayeip_system.entity.LeaveRequest;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Long> {
    
}
