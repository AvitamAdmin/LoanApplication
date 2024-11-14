package com.avitam.bankloanapplication.web.controllers.admin.phonenumberverification;

//import com.twilio.Twilio;
//import com.twilio.rest.verify.v2.service.Verification;
//import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.avitam.bankloanapplication.core.service.CoreService;

import com.avitam.bankloanapplication.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.twilio.example.ValidationExample.ACCOUNT_SID;
import static com.twilio.example.ValidationExample.AUTH_TOKEN;

//import static com.twilio.example.ValidationExample.ACCOUNT_SID;
//import static com.twilio.example.ValidationExample.AUTH_TOKEN;

@RestController
@RequestMapping(path = "api/phoneNumber")
@Slf4j
public class PhoneNumberVerificationController {

    @Autowired
    CoreService coreService;

    @Autowired
    private UserRepository userRepository;

    /*public String getMobileNumber(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user = userRepository.findByEmail(principalObject.getUsername());
        return  user.getMobileNumber();
    }*/

    @GetMapping(value = "/generateOTP")
    public ResponseEntity<String> generateOTP(){

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principalObject = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user = userRepository.findByEmail(principalObject.getUsername());*/

        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        Verification verification = Verification.creator(
                        "VA41d3e9616fccd53101561916756f8281", // this is your verification sid
                "+919789103778", //this is your Twilio verified recipient phone number
                        "sms") // this is your channel type
                .create();

        System.out.println(verification.getStatus());

        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());

        return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
    }

    @GetMapping("/verifyOTP")
    public ResponseEntity<?> verifyUserOTP() throws Exception {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        try {

            VerificationCheck verificationCheck = VerificationCheck.creator(
                            "VA41d3e9616fccd53101561916756f8281")
                    .setTo("+919789103778")
                    .setCode("916643")
                    .create();

            System.out.println(verificationCheck.getStatus());

        } catch (Exception e) {
            return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("This user's verification has been completed successfully", HttpStatus.OK);
    }

}
