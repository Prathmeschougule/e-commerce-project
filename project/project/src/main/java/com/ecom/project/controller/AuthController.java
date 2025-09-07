package com.ecom.project.controller;

import com.ecom.project.model.AppRole;
import com.ecom.project.model.Role;
import com.ecom.project.model.User;
import com.ecom.project.repository.RoleRepository;
import com.ecom.project.repository.UserRepository;
import com.ecom.project.security.jwt.JwtUtils;
import com.ecom.project.security.jwt.LoginRequest;
import com.ecom.project.security.request.SignupRequest;
import com.ecom.project.security.response.MessageResponse;
import com.ecom.project.security.response.UserInfoResponse;
import com.ecom.project.security.services.UserDetailsImp;
import com.ecom.project.security.services.UserDetailsServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(), roles, jwtToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @Valid  @RequestBody SignupRequest signupRequest){
       if (userRepository.existsByUserName(signupRequest.getUserName())){
           return ResponseEntity
                   .badRequest()
                   .body(new MessageResponse("Error : user name is already taken !!"));
       }
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error : email id  is already taken !!"));
        }

        User user = new User(
                signupRequest.getUserName(),
                signupRequest.getEmail(),
               encoder.encode(signupRequest.getPassword())
        );

        Set<String> strRole = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRole == null){
            Role  userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(()->new RuntimeException("Error:Role is not found "));

            roles.add(userRole);
        }else {
            strRole.forEach(role -> {
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Error:Role is not found "));
                        roles.add(adminRole);
                        break;

                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(()->new RuntimeException("Error:Role is not found "));
                        roles.add(sellerRole);
                        break;

                    default:
                        Role  userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(()->new RuntimeException("Error:Role is not found "));

                        roles.add(userRole);
                }
            });
        }
         user.setRole(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Register Succsessfully!!"));

    }

}
