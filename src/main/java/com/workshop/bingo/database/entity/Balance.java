package com.workshop.bingo.database.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Balance {
    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Client client;

    private Float balance;

    @CreationTimestamp
    private Timestamp insertDt;

    @UpdateTimestamp
    private Timestamp updateDt;

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }    

    public Timestamp getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(Timestamp insertDt) {
        this.insertDt = insertDt;
    }

    public Timestamp getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Timestamp updateDt) {
        this.updateDt = updateDt;
    }

    public void up(Float amount) {
        this.balance += amount;
    }

    public void down(Float amount) {
        this.balance -= amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}