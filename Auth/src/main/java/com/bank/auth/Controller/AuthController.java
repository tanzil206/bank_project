package com.bank.auth.Controller;

import com.bank.auth.Model.User;
import com.bank.auth.Response.LoginResponse;
import com.bank.auth.Service.AuthenticationService;
import com.bank.auth.Service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity register(@RequestBody User registerUser) {
        User registeredUser = authenticationService.signup(registerUser);

        if (registeredUser != null) {
            return ResponseEntity.ok("\"message\": \"User registered successfully\"");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not added");
        }


    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody User loginUser) {
        User authenticatedUser = authenticationService.authenticate(loginUser); // user data fetch

        String jwtToken = jwtService.generateToken(authenticatedUser); // generating token

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());


        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token creation failed");
        }

    }
}
