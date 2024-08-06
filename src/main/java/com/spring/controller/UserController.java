package com.spring.controller;

import com.spring.config.JwtService;
import com.spring.exception.UnauthorizedException;
import com.spring.model.*;
import com.spring.dto.UserDTO;
import com.spring.service.AuthenticationService;
import com.spring.service.CategoryService;
import com.spring.service.EmailService;
import com.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {
    private final ModelMapper mapper;
    private final UserService userService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationService service;
    private final CategoryService categoryService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserBean  bean) {
        RegisterRequest request = mapper.map(bean,RegisterRequest.class);
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping(value = "/show-by-id/{id}")
    public UserBean showUser(@PathVariable Integer id) {
        Optional<UserDTO> dto = userService.getUser(id);
        UserBean bean = mapper.map(dto, UserBean.class);
        return bean;
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String jwt = authHeader.substring(7);
            if (jwt.split("\\.").length != 3) { // Validate token format
                throw new UnauthorizedException("JWT strings must contain exactly 3 period characters.");
            }
            // Proceed with token validation and logout logic
        } else {
            throw new UnauthorizedException("Authorization header is missing or invalid.");
        }
        // Clear security context and perform logout
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/send-otp")
    public void verify(@RequestBody EmailBean bean){
        String email = bean.getEmail();
        //System.out.println(email);
        UserDTO dto = userService.isEmailRegister(email);
        if(dto != null){
            Random random = new Random();
            int otp = random.nextInt(1000000);
            String otpNumber = String.format("%06d",otp);
            String otpAndText = otpNumber + " is your verification code.";
            emailService.sendOTPEmail(email,"Verification code",otpAndText);
            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(3); // OTP valid for 5 minutes
            EmailService.storeOTP(email, otpNumber, expiryTime);
            System.out.println("successfully");
        }else{
            System.out.println("email is not login");
        }
    }

    @PostMapping(value = "/verify-otp")
    public int verifyOtp(@RequestBody EmailOTPBean bean ) {
        int result =0;
        System.out.println(bean.getEmail());
        System.out.println(bean.getOtpCode());
        boolean isValid = EmailService.verifyOTP(bean.getEmail(), bean.getOtpCode());
        System.out.println(isValid);
        if(isValid ){
            result = 1;
            return  result;
        }else {
            return result;
        }

    }

    @PostMapping(value = "/update-password")
    public UserDTO updatePassword(@RequestParam("password") String password, @RequestParam("email") String email){
        return userService.updatePassword(password,email);
    }

}
