package de.demmer.dennis.odrauserservice.controller;

import de.demmer.dennis.odrauserservice.exception.AppException;
import de.demmer.dennis.odrauserservice.model.Role;
import de.demmer.dennis.odrauserservice.model.RoleName;
import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.payload.*;
import de.demmer.dennis.odrauserservice.repository.RoleRepository;
import de.demmer.dennis.odrauserservice.repository.UserRepository;
import de.demmer.dennis.odrauserservice.security.CurrentUser;
import de.demmer.dennis.odrauserservice.security.JwtTokenProvider;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PostMapping("/changePassword")
    public ApiResponse changePW(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @CurrentUser UserPrincipal currentUser) {

        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new AppException("User not found"));
        if(changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())){
            return  new ApiResponse(false,"Passwords are equal. Please choose a different password.");
        }

        if (changePasswordRequest.getOldPassword().equals(user.getPassword())){
            user.setPassword(changePasswordRequest.getNewPassword());
            userRepository.save(user);
            return new ApiResponse(true, "Password successfully changed");
        }

        return new ApiResponse(false, "Old password is wrong");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/isAdmin")
    public ApiResponse isAdmin(@CurrentUser UserPrincipal currentUser) {
        return new ApiResponse(true, "Logged in as admin");
    }


}
