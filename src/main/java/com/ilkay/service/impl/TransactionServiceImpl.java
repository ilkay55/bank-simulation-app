package com.ilkay.service.impl;


import com.ilkay.enums.AccountType;
import com.ilkay.exceptions.AccountOwnershipException;
import com.ilkay.exceptions.BadRequestException;
import com.ilkay.exceptions.BalanceNotSufficientException;
import com.ilkay.exceptions.UnderConstructionException;
import com.ilkay.model.Account;
import com.ilkay.model.Transaction;
import com.ilkay.repository.AccountRepository;
import com.ilkay.repository.TransactionRepository;
import com.ilkay.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount,
                                    Date creationDate, String message) {

        if (!underConstruction) {
        /*
               -if sender or receiver is null ?
               -if sender and receiver is the same account ?
               -if sender has enough balance to make transfer ?
               -if both accounts are checking, if not, one of them saving, it needs to be same userId
         */

            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);



        /*
            after all validations are completed, and money is transferred,
            we need to create Transaction object and save/return it.
         */

            Transaction transaction = Transaction.builder().amount(amount)
                    .sender(sender.getId()).receiver(receiver.getId())
                    .createDate(creationDate).message(message).build();

            //save into the db and return it
            return transactionRepository.save(transaction);


        } else {
            throw new UnderConstructionException("App is under construction, please try again later.");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if (checkSenderBalance(sender, amount)) {
            //update sender and receiver balance
            //100 - 80
            sender.setBalance(sender.getBalance().subtract(amount));
            //50 + 80
            receiver.setBalance(receiver.getBalance().add(amount));
        } else {
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }

    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;

    }

    //
    private void checkAccountOwnership(Account sender, Account receiver) {
        /*
            write an if statement that checks if one of the account is saving,
            and user of sender or receiver is not the same, throw AccountOwnershipException
         */
        if ((sender.getAccountType().equals(AccountType.SAVING)
                || receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())) {
            throw new AccountOwnershipException
                    ("If one of the account is saving, user must be the same for sender and receiver");
        }
    }

    private void validateAccount(Account sender, Account receiver) {
        /*
            -if any of the account is null
            -if account ids are the same(same account)
            -f the account exist in the database (repository)
         */
        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        //if accounts are the same throw BadRequestException with saying accounts needs to be different
        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Sender account needs to be different than receiver account");
        }

//        findAccountById(sender.getId());
//        findAccountById(receiver.getId());


    }

    //
//    private void findAccountById(UUID id) {
//        accountRepository.findById(id);
//    }
//
    @Override
    public List<Transaction> findAllTransaction() {

        return transactionRepository.findAll();
    }
//
    @Override
    public List<Transaction> last10Transactions() {
        return transactionRepository.findLast10Transactions();
    }
//
    @Override
    public List<Transaction> findTransactionListById(UUID id) {
       return transactionRepository.findTransactionListByAccountId(id);}

}

