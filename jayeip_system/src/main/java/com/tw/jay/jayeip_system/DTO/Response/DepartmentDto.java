package com.tw.jay.jayeip_system.DTO.Response;

import lombok.Data;

@Data
public class DepartmentDto {
    private Long id;
    private String deptName;
    private int totalEmployees;
}
