package com.spring.dao;

import com.spring.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;

public interface UserDAO extends JpaRepository<UserDTO,Integer> {

    @Query("SELECT u FROM UserDTO u WHERE u.email = ?1 AND u.password = ?2")
    public  UserDTO getLogin( String email,  String password);

    @Query("select u from UserDTO u where u.email = :email")
    public UserDTO getByEmail(String email);

    public Optional<UserDTO> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update UserDTO u set u.emailOTP = ?1, u.emailOTPCreatedAt = ?2 where u.email = ?3")
    int updateOTP(String otp, String time, String email);

    @Query("select u from UserDTO u where u.emailOTP=?1 and u.email= ?2")
    public UserDTO checkOTPNumber(String otp, String email);

    @Query("select u from UserDTO u where u.email = ?1")
    public UserDTO isEmailRegister(String email);

    @Query("update UserDTO u set u.password = ?1 where u.email = ?2")
    public UserDTO updatePassword(String password, String email);
}
