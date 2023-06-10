package com.devil.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BackendApplication {

	private final OtpService otpService;
	public BackendApplication(OtpService otpService) {
		this.otpService = otpService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@GetMapping("/cb/get-otp")
	public String getOtp(){
		// Call your OTP service method to generate and store OTP
		String email = "thakkarnetram10@outlook.com";
		String otp = otpService.generateOtp();
		otpService.storeOtp(email, otp);
		// Call your email sending logic
		otpService.sendOtpByEmail(email, otp);
		return "OTP sent to : " + email + otp;
	}

	@GetMapping("/cb/verify-otp")
	public String verifyOtp(@RequestParam("otp") String otp) {
		String email = "thakkarnetram10@outlook.com";
		boolean isValid = otpService.verifyOtp(email, otp);

		if (isValid) {
			return "OTP is valid";
		} else {
			return "OTP is invalid";
		}
	}
}
