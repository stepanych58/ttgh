package com.stbegradleapp.fixer.controllers;

import com.stbegradleapp.fixer.config.jwt.ExpiredTokensCashe;
import com.stbegradleapp.fixer.config.jwt.JwtUtils;
import com.stbegradleapp.fixer.dto.UserDTO;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.JwtResponse;
import com.stbegradleapp.fixer.model.LoginRequest;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.servises.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ExpiredTokensCashe expiredTokensCashe;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        FixerUser fixerUser = userService.findByPhoneNumber(userDetails.getUsername()).orElse(null);
        if (fixerUser != null) {
            return ResponseEntity.ok(new JwtResponse(jwt,
                    fixerUser.getId(),
                    userDetails.getUsername(),
                    fixerUser.getPhoneNumber(),
                    roles));
        } else {
             return ResponseEntity.ok(new JwtResponse(jwt,
                    BigInteger.ZERO,
                    "1",
                    "1",
                    roles));
        }
    }

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody UserDTO user) {
        FixerUser toSave = new FixerUser(user.getFirstName(), user.getPhoneNumber(), passwordEncoder.encode(user.getPassword()));
        FixerUser saved = userService.save(toSave);
        return authenticateUser(new LoginRequest(user.getPhoneNumber(), user.getPassword()));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader Map<String, String> request, Authentication authentication) {
        String authorization = request.get("authorization");
        String token = JwtUtils.parseJwt(authorization);
        if (authorization != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            expiredTokensCashe.addToken(token, userDetails.getUsername());
        }
        return ResponseEntity.ok(new JwtResponse("jwt",
                BigInteger.ZERO,
                "1",
                "1",
                Collections.singletonList("roles")));
    }


    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }

}
