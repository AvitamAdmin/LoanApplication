package com.avitam.bankloanapplication.web.controllers;

import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.tokenGeneration.JWTUtility;
import com.avitam.bankloanapplication.tokenGeneration.JwtRequest;
import com.avitam.bankloanapplication.tokenGeneration.JwtResponse;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class TokenGenerationController extends BaseController {

    @Autowired
     private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    CustomerRepository userRepository;

    @PostMapping("/api/authenticate")
    @ResponseBody
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest){

        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @PostMapping("api/generateOTP")
    public ResponseEntity<String> generateOTP(@RequestBody JwtRequest jwtRequest){

        /*UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        User user = userRepository.findByUsername(userDetails.getUsername());*/

        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        Verification verification = Verification.creator(
                        "VA41d3e9616fccd53101561916756f8281", // this is your verification sid
                        jwtRequest.getUsername(), //this is your Twilio verified recipient phone number
                        "sms") // this is your channel type
                .create();

        System.out.println(verification.getStatus());

        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());

        return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
    }

}
