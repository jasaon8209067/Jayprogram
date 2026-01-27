package com.tw.jay.jayeip_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tw.jay.jayeip_system.DTO.Request.AuthRequestDto;
import com.tw.jay.jayeip_system.entity.Employee;
import com.tw.jay.jayeip_system.repository.EmployeeRepo;

@Service
public class AuthService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void regoster(AuthRequestDto dto) {
        //檢查帳號是否重複
        if (employeeRepo.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("帳號已存在");
        }

        Employee emp = new Employee();
        emp.setUsername(dto.getUsername());
        //加密密碼
        emp.setPassword(passwordEncoder.encode(dto.getPassword()));
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setPosition(dto.getPosition());

        // 註冊時預設給 ROLE_ADMIN(方便首次註冊測試審核功能)
        // 之後可以根據需求改成 ROLE_USER
        emp.setRole("ROLE_USER");
        employeeRepo.save(emp);
    }
}
