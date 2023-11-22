package com.ilkay;

import com.ilkay.enums.AccountType;
import com.ilkay.model.Account;
import com.ilkay.service.AccountService;
import com.ilkay.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {

        ApplicationContext container= SpringApplication.run(BankSimulationAppApplication.class, args);

//        get account and transaction service beans
//        AccountService accountService = container.getBean(AccountService.class);
//        TransactionService transactionService = container.getBean(TransactionService.class);
////
////        create 2 accounts sender and receiver
//        Account sender = accountService.createNewAccount
//                (BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
//        Account receiver = accountService.createNewAccount
//                (BigDecimal.valueOf(50), new Date(), AccountType.SAVING, 2L);
//        Account receiver2 = accountService.createNewAccount(BigDecimal.valueOf(5000), new Date(), AccountType.CHECKING, 123L);
//        Account receiver3 = accountService.createNewAccount(BigDecimal.valueOf(7500), new Date(), AccountType.SAVING, 124L);
//       Account receiver2=null;
////
//        accountService.listAllAccount().forEach(System.out::println);
//
//        transactionService.makeTransfer(sender,receiver,new BigDecimal(40),new Date(),"Transaction 1");
//
//        System.out.println(transactionService.findAllTransaction().get(0));
//
//        accountService.listAllAccount().forEach(System.out::println);


    }
}
