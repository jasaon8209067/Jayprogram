package com.tw.jay.jayeip_system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * 測試 API：用來確認 JWT Filter 是否成功解析身分
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser() {
        // 從 SecurityContext 中取出剛剛由 JwtAuthenticationFilter 存進去的認證資訊
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        Map<String, String> response = new HashMap<>();
        response.put("username", currentUsername);
        response.put("status", "驗證成功，你已進入受保護的區域");

        return ResponseEntity.ok(response);
    }
}
