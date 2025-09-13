package com.ecom.project.controller;

import com.ecom.project.model.AppRole;
import com.ecom.project.model.Role;
import com.ecom.project.model.User;
import com.ecom.project.repository.RoleRepository;
import com.ecom.project.repository.UserRepository;
import com.ecom.project.security.jwt.JwtUtils;
import com.ecom.project.security.request.LoginRequest;
import com.ecom.project.security.request.SignupRequest;
import com.ecom.project.security.response.MessageResponse;
import com.ecom.project.security.response.UserInfoResponse;
import com.ecom.project.security.services.UserDetailsImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest  loginRequest) {
        System.out.println("Login request: username=" + loginRequest.getUsername() + ", password=" + loginRequest.getPassword());
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

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(), roles);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                jwtCookie.toString())
                .body(response);
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            // Check if username is already taken
            if (userRepository.existsByUserName(signupRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }
            // Check if email is already taken
            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already taken!"));
            }

            // Create new user
            User user = new User(
                    signupRequest.getUsername(),
                    signupRequest.getEmail(),
                    encoder.encode(signupRequest.getPassword())
            );

            Set<String> strRoles = signupRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role.toLowerCase()) {
                        case "admin":
                            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            roles.add(adminRole);
                            break;
                        case "seller":
                            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            roles.add(sellerRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

        } catch (ConstraintViolationException e) {
            // Handle validation errors
            String errorMessage = e.getConstraintViolations().stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Validation Error: " + errorMessage));
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Failed to register user - " + e.getMessage()));
        }
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser( @Valid  @RequestBody SignupRequest signupRequest){
//       if (userRepository.existsByUserName(signupRequest.getUsername())){
//           return ResponseEntity
//                   .badRequest()
//                   .body(new MessageResponse("Error : user name is already taken !!"));
//       }
//        if (userRepository.existsByEmail(signupRequest.getEmail())){
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error : email id  is already taken !!"));
//        }
//
//        User user = new User(
//                signupRequest.getUsername(),
//                signupRequest.getEmail(),
//               encoder.encode(signupRequest.getPassword())
//        );
//
//        Set<String> strRole = signupRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRole == null){
//            Role  userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
//                    .orElseThrow(()->new RuntimeException("Error:Role is not found "));
//
//            roles.add(userRole);
//
//        }else {
//            strRole.forEach(role -> {
//                switch (role){
//                    case "admin":
//                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
//                                .orElseThrow(()->new RuntimeException("Error:Role is not found "));
//                        roles.add(adminRole);
//                        break;
//
//                    case "seller":
//                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
//                                .orElseThrow(()->new RuntimeException("Error:Role is not found "));
//                        roles.add(sellerRole);
//                        break;
//
//                    default:
//                        Role  userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
//                                .orElseThrow(()->new RuntimeException("Error:Role is not found "));
//
//                        roles.add(userRole);
//                }
//            });
//        }
//
//         user.setRoles(roles);
//         userRepository.save(user);
//         return ResponseEntity.ok(new MessageResponse("User Register Succsessfully!!"));
//
//    }

}
