package com.workshop.bingo.services.dto;

import java.util.Date;

import com.workshop.bingo.database.entity.Bet;

public class BetDTO {
    private Integer betId;
    private Integer status;
    private Float stake;
    private Float win;
    private Date placedDate;
    private Date settleDate;
    private Integer ticketId;
    private DrawDTO draw;

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

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public DrawDTO getDraw() {
        return draw;
    }

    public void setDraw(DrawDTO draw) {
        this.draw = draw;
    }
}