package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    private static final Map<String, OTPDetails> otpStore = new HashMap<>();

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOTPEmail(String toEmail, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(messageBody);
        javaMailSender.send(message);
    }

    public static class OTPDetails {
        String otp;
        LocalDateTime expiryTime;

        public OTPDetails(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }

    public static void storeOTP(String email, String otp, LocalDateTime expiryTime) {
        otpStore.put(email, new OTPDetails(otp, expiryTime));
    }

    public static boolean verifyOTP(String email, String otp) {
        OTPDetails otpDetails = otpStore.get(email);
        if (otpDetails != null && otpDetails.otp.equals(otp) && LocalDateTime.now().isBefore(otpDetails.expiryTime)) {
            otpStore.remove(email); // Remove OTP after successful verification
            return true;
        }
        return false;
    }
    

}
