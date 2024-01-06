package com.ilkay.service;

import com.ilkay.dto.AccountDTO;
import com.ilkay.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {
   void createNewAccount(AccountDTO accountDTO);

    Object listAllAccount();

    void deleteAccount(Long id);


    void activateAccount(Long id);

    AccountDTO retrieveById(Long id);

    List<AccountDTO> listAllActiveAccount();

    void updateAccount(AccountDTO accountDTO);

}
