package com.tw.jay.jayeip_system.DTO.Response;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;
    private String name;
    private String email;
    private String position;
    private String departmentName;
    private Long approverId;

}
