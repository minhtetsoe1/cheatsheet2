package com.spring.service;

import com.spring.dao.UserDAO;
import com.spring.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDAO userRepo;

    public Optional<UserDTO> getUser(Integer id) {
        return userRepo.findById(id);
    }

    public List<UserDTO> showAllUser() {
        return userRepo.findAll();
    }

    public UserDTO addUser(UserDTO dto) {
        UserDTO user = null;
        UserDTO userDTO = userRepo.getByEmail(dto.getEmail());
        if (userDTO == null) {
            user = userRepo.save(dto);
            return user;
        } else {
            return user;

        }

    }

    public ResponseEntity<UserDTO> login(String email, String password) {
        UserDTO user = userRepo.getLogin(email, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public Optional<UserDTO> getUserById(Integer id) {
        return userRepo.findById(id);
    }

    public UserDTO isEmailRegister(String email){
        return userRepo.isEmailRegister(email);
    }

    @Transactional
    public int updateOTP(String otp, String time, String email){
         return userRepo.updateOTP(otp,time,email);
    }

    public UserDTO checkOTPNumber(String otp, String email){
        return userRepo.checkOTPNumber(otp,email);
    }

    public UserDTO updatePassword(String password, String email){
        return userRepo.updatePassword(password,email);
    }
}
