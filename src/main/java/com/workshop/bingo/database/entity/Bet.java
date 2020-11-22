package com.workshop.bingo.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Bet {
    public final static Integer STATUS_OPEN = 0;
    public final static Integer STATUS_WON = 1;
    public final static Integer STATUS_LOSE = -1;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Integer status;

    private Float stake;

    private Float win;

    private String selectionData;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp placeDt;

    @UpdateTimestamp
    private Timestamp settleDt;

    @ManyToOne
    @JoinColumn(name = "draw_id", insertable = true, updatable = false)
    private Draw draw;

    @ManyToOne
    @JoinColumn(name = "ticket_id", insertable = true, updatable = false)
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = true, updatable = false)
    private Client client;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Timestamp getSettleDt() {
        return settleDt;
    }

    public void setSettleDt(Timestamp settleDt) {
        this.settleDt = settleDt;
    }

    public Timestamp getPlaceDt() {
        return placeDt;
    }

    public void setPlaceDt(Timestamp placeDt) {
        this.placeDt = placeDt;
    }

    public Draw getDraw() {
        return this.draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getSelectionData() {
        return selectionData;
    }

    public void setSelectionData(String selectionData) {
        this.selectionData = selectionData;
    }
}