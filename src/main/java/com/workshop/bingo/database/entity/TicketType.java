package com.workshop.bingo.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class TicketType {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Float price;

    private Float odds;

    private Integer numberOfSelections;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getOdds() {
        return odds;
    }

    public void setOdds(Float odds) {
        this.odds = odds;
    }

    public Integer getNumberOfSelections() {
        return numberOfSelections;
    }

    public void setNumberOfSelections(Integer numberOfSelections) {
        this.numberOfSelections = numberOfSelections;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}