package com.ilkay.service;

import com.ilkay.enums.AccountType;
import com.ilkay.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import java.util.UUID;

public interface AccountService {
    Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userID);

    Object listAllAccount();

    void deleteAccount(UUID id);


    void activateAccount(UUID id);

    Account retrieveById(UUID id);

}
