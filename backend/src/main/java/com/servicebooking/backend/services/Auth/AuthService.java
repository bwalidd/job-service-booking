package com.servicebooking.backend.services.Auth;

import com.servicebooking.backend.dto.SignUpDto;
import com.servicebooking.backend.dto.UserDto;

public interface AuthService {

    UserDto SignupClient(SignUpDto signUpDto);
    Boolean presentByEmail(String email);
    UserDto SignupCompany(SignUpDto signUpDto);
}
