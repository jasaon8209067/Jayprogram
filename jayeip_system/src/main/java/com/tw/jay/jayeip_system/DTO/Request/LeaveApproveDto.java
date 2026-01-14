package com.tw.jay.jayeip_system.DTO.Request;

import lombok.Data;

@Data
public class LeaveApproveDto {
    private Long leaveRequestId;
    private String status; // APPROVED or REJECTED
    private Long approverId;
}
