package com.bank.account.Controller;

import com.bank.account.Model.Account;
import com.bank.account.Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAccountByNumber_Success() {
        // Arrange
        String accountNumber = "ACC123456";
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        when(accountService.getAccountByNumber(accountNumber)).thenReturn(Optional.of(account));

        // Act
        ResponseEntity<?> response = accountController.getAccountByNumber(accountNumber);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(account), response.getBody());
    }

    @Test
    public void testGetAccountByNumber_NotFound() {
        // Arrange
        String accountNumber = "ACC12345";
        when(accountService.getAccountByNumber(accountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            accountController.getAccountByNumber(accountNumber);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Account not found", exception.getReason());
    }
}
