package com.spring.service;


import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Map<String, OTPDetails> otpStore = new HashMap<>();

    @Autowired
    private JavaMailSender javaMailSender;

    public static void sendOTPEmail(String toEmail, String subject, String messageBody) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("minhtetsoe186@gmail.com", "hgna etod wjvx xhna");
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageBody);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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
