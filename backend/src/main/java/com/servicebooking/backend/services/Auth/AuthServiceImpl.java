package com.servicebooking.backend.services.Auth;


import com.servicebooking.backend.dto.SignUpDto;
import com.servicebooking.backend.entity.User;
import com.servicebooking.backend.enums.UserRole;
import com.servicebooking.backend.dto.UserDto;
import com.servicebooking.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    public UserDto SignupClient(SignUpDto signUpDto){
        User user = new User();

        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPhone(signUpDto.getPhone());
        user.setLastName(signUpDto.getLastName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpDto.getPassword()));

        user.setRole(UserRole.CLIENT);

        return userRepository.save(user).getDto();
    }

    public UserDto SignupCompany(SignUpDto signUpDto){
        User user = new User();

        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPhone(signUpDto.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpDto.getPassword()));

        user.setRole(UserRole.COMPANY);

        return userRepository.save(user).getDto();
    }

    @Override
    public Boolean presentByEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }
}
