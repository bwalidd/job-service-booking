package com.servicebooking.backend.services.jwt;

import com.servicebooking.backend.entity.User;
import com.servicebooking.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User u = userRepository.findFirstByEmail(email);
        if (u == null)
            throw new UsernameNotFoundException("user not found",null);
        return new org.springframework.security.core.userdetails.User(u.getEmail(),u.getPassword(),new ArrayList<>());

    }
}
