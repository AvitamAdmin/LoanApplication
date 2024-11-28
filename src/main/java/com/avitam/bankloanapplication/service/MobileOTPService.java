package com.avitam.bankloanapplication.service;

import com.avitam.bankloanapplication.model.dto.UserDto;

public interface MobileOTPService {


    public UserDto sendOtp(UserDto userDto);

    public UserDto validateOtp(UserDto userDto);

    public UserDto saveUsername(UserDto userDto);
}
