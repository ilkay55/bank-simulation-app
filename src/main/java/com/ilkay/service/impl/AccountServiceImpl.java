package com.ilkay.service.impl;

import com.ilkay.enums.AccountType;
import com.ilkay.model.Account;
import com.ilkay.service.AccountService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    this.accountRepository = accountRepository;}

    @Override
    public Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        //we need to create Account object
        Account account = Account.builder().id(UUID.randomUUID()).userId(userId).balance(balance).accountType(accountType)
                .creationDate(createDate).build();
        //save into the database(repository)
        //return the object created
        return accountRepository.save(acoocunt);
    }

    @Override
    public List<Account> listAllAccount() {

        return null;
    }
}
