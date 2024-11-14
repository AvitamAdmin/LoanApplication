package com.avitam.bankloanapplication.core.service;


import com.avitam.bankloanapplication.model.dto.CustomerDTO;
import com.avitam.bankloanapplication.model.entity.User;
import com.avitam.bankloanapplication.model.entity.VerificationToken;


public interface UserService {


    void save(CustomerDTO request);


    User findByUserName(String userName);

    User findByEmail(String email);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void saveRegisteredUser(User user);

    String validateVerificationToken(String token);

    User getUser(String verificationToken);

    boolean isAdminRole();


    boolean updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    boolean updateOtp(String token, String email);

    void updatePassword(User user, String password);

}
