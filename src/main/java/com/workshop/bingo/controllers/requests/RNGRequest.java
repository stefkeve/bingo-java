package com.workshop.bingo.controllers.requests;

import javax.validation.constraints.Min;

public class RNGRequest {
    @Min(1)
    private Integer from;

    private Integer to;

    private Integer number;

    private Boolean repeat;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }
}