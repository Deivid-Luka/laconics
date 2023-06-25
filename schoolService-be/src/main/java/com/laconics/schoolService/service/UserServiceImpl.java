package com.laconics.schoolService.service;

import com.laconics.schoolService.DTO.Authentification.AuthenticationDTO;
import com.laconics.schoolService.entity.Role;
import com.laconics.schoolService.entity.User;
import com.laconics.schoolService.exception.CustomExceptions;
import com.laconics.schoolService.repository.RoleRepository;
import com.laconics.schoolService.repository.UserRepository;
import com.laconics.schoolService.securityConfig.AuthenticationConfig;
import com.laconics.schoolService.securityConfig.JWTTokenFunctions;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
public class UserServiceImpl implements UserService {


    private final JWTTokenFunctions jwtTokenFunctions;
    private final AuthenticationConfig authenticationConfig;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public UserServiceImpl(JWTTokenFunctions jwtTokenFunctions, AuthenticationConfig authenticationConfig, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.jwtTokenFunctions = jwtTokenFunctions;
        this.authenticationConfig = authenticationConfig;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public User save(User user) throws RoleNotFoundException,CustomExceptions.UserExistsException {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new CustomExceptions.UserExistsException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findById(2L).orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.setRole(role);
        return userRepository.save(user);
    }



    @Override
    public String authenticate(AuthenticationDTO user) {
        Authentication authentication = authenticationConfig.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return jwtTokenFunctions.generateToken(authentication);
    }
}
