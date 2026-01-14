package com.tw.jay.jayeip_system.DTO.Response;

import lombok.Data;

@Data
public class LeaveRequestResponseDto {
    private Long id;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;
    private String approver;
}
