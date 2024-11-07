package com.bank.auth.Controller;

import com.bank.auth.Model.User;
import com.bank.auth.Response.LoginResponse;
import com.bank.auth.Service.AuthenticationService;
import com.bank.auth.Service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authenticationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        // Arrange
        User registerUser = new User();
        registerUser.setUserName("tanzil2");
        registerUser.setEmail("tan@gmail.com");
        registerUser.setFullName("Tanzir Ahmed");

        User registeredUser = new User();
        registeredUser.setId(1L);
        registerUser.setUserName("tanzil2");
        registerUser.setEmail("tan@gmail.com");
        registerUser.setFullName("Tanzir Ahmed");

        when(authenticationService.signup(registerUser)).thenReturn(registeredUser);

        // Act
        ResponseEntity<User> response = authenticationController.register(registerUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registeredUser, response.getBody());
    }

    @Test
    public void testAuthenticate_Success() {
        // Arrange
        User loginUser = new User();
        loginUser.setUserName("tanzil2");
        loginUser.setPassword("$2a$10$4ZDcwqgD2MLIx50oJ0zkWuo0xjMRgNUcWSDOMssArHgfQ4mk0lmk2");

        User authenticatedUser = new User();
        authenticatedUser.setId(1L);
        authenticatedUser.setUserName("tanzil2");

        String token = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
        long expiresIn = 3600L;

        when(authenticationService.authenticate(loginUser)).thenReturn(authenticatedUser);
        when(jwtService.generateToken(authenticatedUser)).thenReturn(token);
        when(jwtService.getExpirationTime()).thenReturn(expiresIn);

        // Act
        ResponseEntity<LoginResponse> response = authenticationController.authenticate(loginUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody().getToken());

    }
}

