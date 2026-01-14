package com.tw.jay.jayeip_system.DTO.Request;

import lombok.Data;

@Data
public class EmployeeCreate {
    private String name;
    private String email;
    private String position;
    private Long departmentId;
    private Long approverId;
    
}
