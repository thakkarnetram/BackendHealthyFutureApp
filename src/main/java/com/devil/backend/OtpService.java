package com.devil.backend;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class OtpService {
    private final UserModalRepository userModalRepository;
    private final JavaMailSender javaMailSender;

    public OtpService(UserModalRepository userModalRepository, JavaMailSender javaMailSender) {
        this.userModalRepository = userModalRepository;
        this.javaMailSender = javaMailSender;
    }

    // Generating otp
    public String generateOtp(){
        int otpLength = 4;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for(int i= 0; i<otpLength;i++){
            int digit = random.nextInt(10);
            otp.append(digit);
        }

        return otp.toString();
    }

    // saving data
    public void storeOtp(String email,String otp){
        UserModal userModal= new UserModal();
        userModal.setEmailId("it@rymo.in");
        userModal.setOtp(otp);
        userModalRepository.save(userModal);
    }

    // verification otp

    public boolean verifyOtp(String email, String otp) {
        List<UserModal> userModals = userModalRepository.findAllByEmailId(email);

        for (UserModal userModal : userModals) {
            if (userModal.getOtp().trim().equals(otp.trim())) {
                // Checking Otp Expiry
                long currentTime = System.currentTimeMillis();
                long otpExpirationTime = currentTime + (5 * 60 * 1000);
                if (currentTime <= otpExpirationTime) {
                    userModalRepository.delete(userModal);
                    return true;
                }
            }
        }
        return false;
    }

    // Sending otp
    public void sendOtpByEmail(String email,String otp){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("it@rymo.in");
        mailMessage.setSubject("OTP Verification Code");
        mailMessage.setText("Here is your Verification Code: " + otp + "\n" +
                "OTP is valid for 5 minutes. Do not share the OTP.");
        javaMailSender.send(mailMessage);
        System.out.println("Email sent to: " + Objects.requireNonNull(mailMessage.getTo())[0]);
    }
}
