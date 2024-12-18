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
    CustomerRepository customerRepository;

    @PostMapping("/api/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest){

        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        return new JwtResponse(token);
    }



}
