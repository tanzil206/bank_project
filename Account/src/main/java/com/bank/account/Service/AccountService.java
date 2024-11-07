package com.bank.account.Service;

import com.bank.account.Model.Account;
import com.bank.account.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;


    public Optional<Account> getAccountByNumber(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber); // get account details by account number
    }

}
