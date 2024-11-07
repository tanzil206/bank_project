package com.bank.account.Controller;

import com.bank.account.Model.Account;
import com.bank.account.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    @Autowired
    AccountService accountService;


    @GetMapping("/customer/{account_number}")
    public ResponseEntity getAccountByNumber(@PathVariable("account_number") String accountNumber) {

        Optional<Account> customer = accountService.getAccountByNumber(accountNumber);

        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

}
