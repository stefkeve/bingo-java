package com.workshop.bingo.database;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class DBUtils {
    @PersistenceContext
    private EntityManager entityManager;

    public Date getCurrentDate() {
        Query query = entityManager.createNativeQuery("SELECT CURRENT_TIMESTAMP()");
        return (Date) query.getSingleResult();
    }
}