package com.workshop.bingo.database.repository;

import org.springframework.data.repository.CrudRepository;
import com.workshop.bingo.database.entity.Balance;

public interface BalanceRepository extends CrudRepository<Balance, Integer> {

}