package com.tw.jay.jayeip_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tw.jay.jayeip_system.DTO.Request.DepartmentAdd;
import com.tw.jay.jayeip_system.DTO.Response.DepartmentDto;
import com.tw.jay.jayeip_system.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartment());
    }

    @PostMapping("/add")
    public ResponseEntity<DepartmentAdd> addDepartment(@RequestBody DepartmentAdd request) {
        departmentService.addDepartment(request);
        return ResponseEntity.ok().build();
    }
}
