package com.tw.jay.jayeip_system.DTO.Request;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String username;
    private String password;
    private String name;
    private String email;
    private String position;
    
}
