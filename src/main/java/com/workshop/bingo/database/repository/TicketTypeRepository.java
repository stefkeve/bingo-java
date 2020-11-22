package com.workshop.bingo.database.repository;

import org.springframework.data.repository.CrudRepository;
import com.workshop.bingo.database.entity.TicketType;

public interface TicketTypeRepository extends CrudRepository<TicketType, Integer> {
    
}