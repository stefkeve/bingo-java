package com.workshop.bingo.database.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.workshop.bingo.database.entity.Bet;
import com.workshop.bingo.database.entity.Client;

public interface BetRepository extends CrudRepository<Bet, Integer> {
    List<Bet> findAllByClient(Client client);
    Bet findByIdAndClient(Integer id, Client client);
}