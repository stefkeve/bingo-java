package com.workshop.bingo.services.dto;

public class NextDrawDTO extends DrawDTO {
    private Integer timeleft;

    public Integer getTimeleft() {
        return timeleft;
    }

    public void setTimeleft(Integer timeleft) {
        this.timeleft = timeleft;
    }
}