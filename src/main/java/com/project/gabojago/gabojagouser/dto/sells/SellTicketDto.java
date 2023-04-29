package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.Date;
@Data
public class SellTicketDto {
    private int stId;
    private int sodId;
    private String ticketNum;
    private boolean useCheck;
    private Date useDate;


}
