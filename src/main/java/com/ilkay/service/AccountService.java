package com.ilkay.service;

import com.ilkay.dto.AccountDTO;
import com.ilkay.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public interface AccountService {
    AccountDTO createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userID);

    Object listAllAccount();

    void deleteAccount(UUID id);


    void activateAccount(UUID id);

    AccountDTO retrieveById(UUID id);

}
