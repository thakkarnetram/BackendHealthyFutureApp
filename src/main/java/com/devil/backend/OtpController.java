package com.devil.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
    @Autowired
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    // Generate Endpoint

    @PostMapping("/cb/get-otp")
    public ResponseEntity<String>generateOtp(@RequestBody Map<String,String> request){
        String email = "it@rymo.in";
        String otp = otpService.generateOtp();
        otpService.storeOtp(email,otp);
        otpService.sendOtpByEmail(email,otp);
        return ResponseEntity.ok("Otp Sent to: " + email);
    }

    // Verification Endpoint
    @PostMapping("/cb/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam("otp") String otp) {
        String email = "it@rymo.in";
        if (otpService.verifyOtp(email, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }
}
