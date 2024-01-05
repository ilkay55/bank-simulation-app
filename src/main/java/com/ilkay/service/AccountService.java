package com.ilkay.service;

import com.ilkay.dto.AccountDTO;
import com.ilkay.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;

public interface AccountService {
   void createNewAccount(AccountDTO accountDTO);

    Object listAllAccount();

    void deleteAccount(Long id);


    void activateAccount(Long id);

    AccountDTO retrieveById(Long id);

}
