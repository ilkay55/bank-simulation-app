package com.ilkay.repository;

import com.ilkay.dto.TransactionDTO;
import com.ilkay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//@Component
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query(value = "SELECT * FROM transaction ORDER BY create_date DESC LIMIT 10", nativeQuery = true)
    List<Transaction> findLast10Transactions():

//    List<Transaction> findTopByOrderByCreateDateDesc;

    //    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();
//
//    public TransactionDTO save(TransactionDTO transactionDTO) {
//        transactionDTOList.add(transactionDTO);
//        return transactionDTO;
//    }
//
//    public List<TransactionDTO> findAll() {
//        return transactionDTOList;
//    }
//
//
    public default List<Transaction> findLast10Transactions() {
        //write a stream that sort the transactions based on creation date
        //and only return 10 of them
        return transactionList.stream()
                .sorted(Comparator.comparing(TransactionDTO::getCreateDate).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
//
//    public List<TransactionDTO> findTransactionListByAccountId(Long id) {
//        //if account id is used either as a sender or receiver, return those transactions
//        return transactionDTOList.stream()
//                .filter(transactionDTO -> transactionDTO
//                        .getSender().equals(id)
//                        || transactionDTO.getReceiver().equals(id))
//                .collect(Collectors.toList());
//    }
//}
