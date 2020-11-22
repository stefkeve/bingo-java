package com.workshop.bingo.controllers.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Placebet {
    @Min(1)
    @Max(3)
    private Integer ticketId;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }
}