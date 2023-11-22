package com.ilkay.controller;

import com.ilkay.model.Transaction;
import com.ilkay.service.AccountService;
import com.ilkay.service.TransactionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model) {
        //what we need to provide to make transfer happen
        //we need to provide empty transaction object
        model.addAttribute("transaction", Transaction.builder().build());

        //we need to provide list of all accounts
        model.addAttribute("accounts", accountService.listAllAccount());

        //we need list of last 10 transactions to fill the table(business logic is missing)
        model.addAttribute("lastTransactions", transactionService.last10Transactions());

        return "transaction/make-transfer";
    }
}
