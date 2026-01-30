package com.tw.jay.jayeip_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tw.jay.jayeip_system.DTO.Request.AuthRequestDto;
import com.tw.jay.jayeip_system.Security.JwtUtils;
import com.tw.jay.jayeip_system.entity.Employee;
import com.tw.jay.jayeip_system.repository.EmployeeRepo;

@Service
public class AuthService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public void register(AuthRequestDto dto) {
        //檢查帳號是否重複
        if (employeeRepo.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("帳號已存在");
        }

        Employee emp = new Employee();
        emp.setUsername(dto.getUsername());

        //加密密碼
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        emp.setPassword(encodedPassword);
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setPosition(dto.getPosition());

        // 註冊時預設給 ROLE_ADMIN(方便首次註冊測試審核功能)
        // 之後可以根據需求改成 ROLE_USER
        emp.setRole("ROLE_USER");
        employeeRepo.save(emp);
    }

    public String login(String username, String password) {
        // 登入邏輯，先找到員工
        System.out.println("正在嘗試登入: " + username);
        Employee emp = employeeRepo.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("帳號或密碼錯誤"));

        // 比對密碼
        if (!passwordEncoder.matches(password, emp.getPassword())) {
            throw new RuntimeException("帳號或密碼錯誤");
        }
        
        // 驗證成功
        return jwtUtils.generateToken(emp.getUsername());

    }
}
