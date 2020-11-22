package com.workshop.bingo.database.repository;

import org.springframework.data.repository.CrudRepository;
import com.workshop.bingo.database.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    
}