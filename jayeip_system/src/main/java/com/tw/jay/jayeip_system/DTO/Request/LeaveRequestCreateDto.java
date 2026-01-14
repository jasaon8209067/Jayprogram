package com.tw.jay.jayeip_system.DTO.Request;

import lombok.Data;

@Data
public class LeaveRequestCreateDto {
    private Long employeeId;
    private String startDate;
    private String endDate;
    private String reason;

}
