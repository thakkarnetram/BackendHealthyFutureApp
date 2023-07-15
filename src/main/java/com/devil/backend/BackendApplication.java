package com.devil.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Import(CorsConfig.class)
@RestController
public class BackendApplication {

	private final OtpService otpService;
	public BackendApplication(OtpService otpService) {
		this.otpService = otpService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@GetMapping("/")
	public String home(){
		return "Home of OTP New";
	}

	@GetMapping("/cb/get-otp")
	public ResponseEntity<String>getOtp(){
		// Call your OTP service method to generate and store OTP
		String email = "it@rymo.in";
		String otp = otpService.generateOtp();
		otpService.storeOtp(email, otp);
		// Call your email sending logic
		otpService.sendOtpByEmail(email, otp);
		return ResponseEntity.ok("OTP Sent to  " + email + " your otp is " + otp);
	}

	@GetMapping("/cb/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestParam("otp") String otp) {
		String email = "it@rymo.in";
		boolean isValid = otpService.verifyOtp(email, otp);

		if (isValid) {
			return ResponseEntity.ok("OTP is valid");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP is invalid");
		}
	}

	@GetMapping("/cb/status")
	public boolean checkStatus() {
		return true;
	}
}
