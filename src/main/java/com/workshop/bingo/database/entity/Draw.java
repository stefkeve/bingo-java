package com.workshop.bingo.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Draw {
    public final static Integer DRAW_STATUS_OPEN = 0;
    public final static Integer DRAW_STATUS_SETTLED = 1;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Integer status;

    private String draw;
    
    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp insertDt;

    @UpdateTimestamp
    private Timestamp updateDt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date drawDt;

    @Formula(value="CURRENT_TIMESTAMP()")
    private Timestamp currentDt;

    //@Formula(value="draw_dt - CURRENT_TIMESTAMP()")
    @Formula(value="TIME_TO_SEC(TIMEDIFF(draw_dt, CURRENT_TIMESTAMP()))")
    private Float timeleft;

    @OneToMany(mappedBy = "draw")
    private Set<Bet> bets;

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

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
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

    public Date getDrawDt() {
        return drawDt;
    }

    public void setDrawDt(Date drawDt) {
        this.drawDt = drawDt;
    }

    public Set<Bet> getBets() {
        return bets;
    }

    public void setBets(Set<Bet> bets) {
        this.bets = bets;
    }

    public Timestamp getCurrentDt() {
        return currentDt;
    }

    public void setCurrentDt(Timestamp currentDt) {
        this.currentDt = currentDt;
    }

    public Float getTimeleft() {
        return timeleft;
    }

    public void setTimeleft(Float timeleft) {
        this.timeleft = timeleft;
    }
}