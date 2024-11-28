package com.avitam.bankloanapplication.web.controllers.admin.phonenumberverification;

import com.avitam.bankloanapplication.model.dto.UserDto;
import com.avitam.bankloanapplication.service.MobileOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class MobileOTPController {

    @Autowired
    private MobileOTPService mobileOTPService;

    @PostMapping("/send-otp")
    public UserDto sendOtp(@RequestBody UserDto userDto) {

        return mobileOTPService.sendOtp(userDto);
    }

    @PostMapping("/validate-otp")
    public UserDto validateOtp(@RequestBody UserDto userDto) {

        return mobileOTPService.validateOtp(userDto);
    }

    @PostMapping("/save-username")
    public UserDto saveUsername(@RequestBody  UserDto userDto){
        return mobileOTPService.saveUsername(userDto);
    }
}
