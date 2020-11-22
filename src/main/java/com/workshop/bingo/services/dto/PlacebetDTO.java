package com.workshop.bingo.services.dto;

import java.util.Date;

public class PlacebetDTO {
    private Integer betId;
    private Integer status;
    private Float stake;
    private Float win;
    private Date placedDate;
    private Integer drawId;
    private Float balance;

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getStake() {
        return stake;
    }

    public void setStake(Float stake) {
        this.stake = stake;
    }

    public Float getWin() {
        return win;
    }

    public void setWin(Float win) {
        this.win = win;
    }

    public Date getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Date placedDate) {
        this.placedDate = placedDate;
    }

    public Integer getDrawId() {
        return drawId;
    }

    public void setDrawId(Integer drawId) {
        this.drawId = drawId;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}