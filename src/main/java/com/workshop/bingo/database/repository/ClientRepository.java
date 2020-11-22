package com.workshop.bingo.database.repository;

import org.springframework.data.repository.CrudRepository;
import com.workshop.bingo.database.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    Client findByEmail(String email);
}