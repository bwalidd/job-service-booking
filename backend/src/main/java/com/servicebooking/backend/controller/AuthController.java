package com.servicebooking.backend.controller;


import com.servicebooking.backend.dto.AuthenticationRequestDto;
import com.servicebooking.backend.dto.SignUpDto;
import com.servicebooking.backend.dto.UserDto;
import com.servicebooking.backend.entity.User;
import com.servicebooking.backend.repository.UserRepository;
import com.servicebooking.backend.services.Auth.AuthService;
import com.servicebooking.backend.services.jwt.UserDetailsImpl;
import com.servicebooking.backend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsImpl userDetailsService;

    public static final String PREFIX= "Bearer ";
    public static final String HEADER_STRING= "Authorization ";

    @PostMapping("client/sign-up")
    public ResponseEntity<?> signupClient(@RequestBody SignUpDto signUpDto) {

        if (authService.presentByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("client already exits", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto user = authService.SignupClient(signUpDto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignUpDto signUpDto) {

        if (authService.presentByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("company already exits", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto user = authService.SignupCompany(signUpDto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @PostMapping("client/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest) {
        try {
            System.out.println("Attempting authentication for email: " + authenticationRequest.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            System.out.println("Authentication successful for email: " + authenticationRequest.getEmail());
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed: Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Load user and generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        User user = userRepository.findFirstByEmail(authenticationRequest.getEmail());

        // Prepare response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("userId", user.getId());
        responseBody.put("userRole", user.getRole());
        responseBody.put("jwt",jwt);

        // Return response with headers
        return ResponseEntity.ok()
                .header("Access-Control-Expose-Headers", "Authorization")
                .header("Authorization", PREFIX + jwt)
                .body(responseBody);
    }


    @PostMapping("company/login")
    public ResponseEntity<?> createAuthenticationTokenCompany(@RequestBody AuthenticationRequestDto authenticationRequest) {
        try {
            System.out.println("Attempting authentication for email: " + authenticationRequest.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            System.out.println("Authentication successful for email: " + authenticationRequest.getEmail());
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed: Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Load user and generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        User user = userRepository.findFirstByEmail(authenticationRequest.getEmail());

        // Prepare response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("userId", user.getId());
        responseBody.put("userRole", user.getRole());
        responseBody.put("jwt",jwt);

        // Return response with headers
        return ResponseEntity.ok()
                .header("Access-Control-Expose-Headers", "Authorization")
                .header("Authorization", PREFIX + jwt)
                .body(responseBody);
    }









}
