package com.ilkay.service;

import com.ilkay.enums.AccountType;
import com.ilkay.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

public interface AccountService {
    Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userID);

    List<Account> listAllAccount();
}
