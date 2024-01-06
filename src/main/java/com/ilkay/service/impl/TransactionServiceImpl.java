package com.ilkay.service.impl;


import com.ilkay.dto.AccountDTO;
import com.ilkay.dto.TransactionDTO;
import com.ilkay.entity.Account;
import com.ilkay.entity.Transaction;
import com.ilkay.enums.AccountType;
import com.ilkay.exceptions.AccountOwnershipException;
import com.ilkay.exceptions.BadRequestException;
import com.ilkay.exceptions.BalanceNotSufficientException;
import com.ilkay.exceptions.UnderConstructionException;
import com.ilkay.mapper.TransactionMapper;
import com.ilkay.repository.TransactionRepository;
import com.ilkay.service.AccountService;
import com.ilkay.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;

    //    private final AccountRepository accountRepository; /for a better design
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountService accountService,
                                  TransactionRepository transactionRepository,
                                  //AccountRepository accountRepository
                                  TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        //this.accountRepository = accountRepository;
    }

    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount,
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

            TransactionDTO transactionDTO = new TransactionDTO(sender, receiver, amount, message, creationDate);/*TransactionDTO.builder().amount(amount)
                    .sender(sender.getId()).receiver(receiver.getId())
                    .createDate(creationDate).message(message).build();*/

            //save into the db and return it
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
            return transactionDTO;


        } else {
            throw new UnderConstructionException("App is under construction, please try again later.");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if (checkSenderBalance(sender, amount)) {
            //update sender and receiver balance
            //100 - 80
            sender.setBalance(sender.getBalance().subtract(amount));
            //50 + 80
            receiver.setBalance(receiver.getBalance().add(amount));

                /*  6MIN
                get the dto from the database for both sender and receiver, update balance and save it.
                create accountService updateAccount method and use it for saving.
             */
            //find sender by id
            AccountDTO senderAcc = accountService.retrieveById(sender.getId());
            senderAcc.setBalance(sender.getBalance());
            accountService.updateAccount(senderAcc);

            AccountDTO receiverAcc = accountService.retrieveById(receiver.getId());
            receiverAcc.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAcc);

        } else {
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }

    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;

    }

    //
    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
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

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
        /*
            -if any of the account is null
            -if account ids are the same(same account)
            -f the account exist in the database (repository)
         */
        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        //if accounts are the same throw BadRequestException with saying accounts needs to be different
//        if (sender.getId().equals(receiver.getId())) {
//            throw new BadRequestException("Sender account needs to be different than receiver account");
//        }

        // findAccountById(sender.getId());
        // findAccountById(receiver.getId());


    }

    private void findAccountById(Long id) {
        accountService.retrieveById(id);
    }

    //
//    private void findAccountById(UUID id) {
//        accountRepository.findById(id);
//    }
//
    @Override
    public List<TransactionDTO> findAllTransaction() {

        List<Transaction> transactionList = transactionRepository.findAll();
        //get the transaction entity for all and return them as a list of transactionDTO
        return transactionRepository.findAll()
                .stream().map(transactionMapper::convertToDTO)
                .collect(Collectors.toList());

    }

    //
    @Override
    public List<TransactionDTO> last10Transactions() {
        //we want last 10 latest transactions
        //write a native query to get the result for last 10 transaction
        List<Transaction> last10Transactions = transactionRepository.findLast10Transactions();

        return last10Transactions.stream()
                .map(transactionMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    //
    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        //get the list of transactions if account id is involved as a sender or receiver
        List<Transaction> transactionList = transactionRepository
                .findTransactionListByAccountId(id);
        //convert list of entity to dto and return it.
        return transactionList.stream()
                .map(transactionMapper::convertToDTO)
                .collect(Collectors.toList());
    }

}

