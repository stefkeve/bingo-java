package com.workshop.bingo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.workshop.bingo.database.entity.Balance;
import com.workshop.bingo.database.entity.Client;
import com.workshop.bingo.database.entity.Transaction;
import com.workshop.bingo.database.repository.BalanceRepository;
import com.workshop.bingo.database.repository.TransactionRepository;
import com.workshop.bingo.services.dto.TransactionDTO;

@Service
@Transactional
public class TransactionService {
    private final static String TRANSACTION_TYPE_WITHDRAW = "withdraw";
    private final static String TRANSACTION_TYPE_DEPOSIT  = "deposit";

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    public TransactionDTO withdraw(Client client, Float amount) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setClient(client);
        transaction.setType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setAmount(amount);
        transactionRepository.save(transaction);

        Balance balance = balanceRepository.findById(client.getId())
                                           .orElseThrow(
                                               () -> new Exception("Missing balance for client")
                                            );
        
        if(balance.getBalance() < amount) {
            throw new Exception("Not enough funds");
        }

        balance.down(amount);
        balanceRepository.save(balance);

        TransactionDTO response = new TransactionDTO();
        response.setTransactionId(transaction.getId());
        response.setBalance(balance.getBalance());

        return response;
    }

    public TransactionDTO deposit(Client client, Float amount) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setClient(client);
        transaction.setType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setAmount(amount);

        transactionRepository.save(transaction);

        Balance balance = balanceRepository.findById(client.getId())
                                           .orElseThrow(
                                               () -> new Exception("Missing balance for client")
                                            );
        balance.up(amount);
        balanceRepository.save(balance);

        TransactionDTO response = new TransactionDTO();
        response.setTransactionId(transaction.getId());
        response.setBalance(balance.getBalance());

        return response;
    }
}