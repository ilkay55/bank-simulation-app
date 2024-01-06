package com.ilkay.service.impl;

import com.ilkay.dto.AccountDTO;
import com.ilkay.entity.Account;
import com.ilkay.enums.AccountStatus;
import com.ilkay.enums.AccountType;
import com.ilkay.repository.AccountRepository;
import com.ilkay.service.AccountService;
import org.springframework.stereotype.Component;
import com.ilkay.mapper.AccountMapper;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {

        //we need to create Account object
        accountDTO.setCreationDate(new Date());
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);

        //save into the database(repository)*/
        //return the object created
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        //we are getting list of account but we need to return list of AccountDTO
        List<Account> accountList = accountRepository.findAll();
        //we are converting entity to dto list and return it
        return accountList
                .stream()
                .map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {

        //find the account object based on id
        Account account = accountRepository.findById(id).get();
//        get yerine daha nizami bu şekilde exception oluşturabiliriz
//                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        //set status to deleted
        account.setAccountStatus(AccountStatus.DELETED);
        // we need to save the account (updated account object)
        accountRepository.save(account);
    }

    @Override
    public void activateAccount(Long id) {
        //find the account belongs the id
        Account account = accountRepository.findById(id).get();
        //set status to active
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
//find the account entity based on id, then convert it dto and return it
        return accountMapper
                .convertToDTO(accountRepository.findById(id).get());
    }

    @Override
    public List<AccountDTO> listAllActiveAccount() {

        List<Account> accountList=accountRepository
                .findAllByAccountStatus(AccountStatus.ACTIVE);

        return accountList.stream().map(accountMapper::convertToDTO)
                .collect(Collectors.toList());

//        List<Account> accountList = accountRepository.findAll();
//        //we are converting entity to dto list and return it
//        return accountList
//                .stream().filter(account -> account.getAccountStatus().equals(AccountStatus.ACTIVE))
//                .map(accountMapper::convertToDTO).collect(Collectors.toList());
    }
}