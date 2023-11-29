package com.example.ch7_8_test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public Map<String, String> Test(@RequestHeader("User-Info") String userInfo) {
        logger.info("요청이 도착함");

        // 사용자 정보를 응답으로 반환
        Map<String, String> userProfile = new HashMap<>();
        userProfile.put("email", userInfo);

        return userProfile;
    }
}