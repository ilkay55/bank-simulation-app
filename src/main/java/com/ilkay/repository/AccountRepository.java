package com.ilkay.repository;

import com.ilkay.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepository {

    public static List<Account> accountList = new ArrayList<>();

    public Account save(Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return accountList;
    }

    public Account findById(UUID id) {
        //TASK
        //complete the method, that find the account inside the list, if not
        //throw RecordNotFoundException
        return accountList.stream().filter(account -> account.getId().equals(id))
                .findAny().orElseThrow(()-> new RecordNotFoundException("Account does not exist in the database."));

    }
}
