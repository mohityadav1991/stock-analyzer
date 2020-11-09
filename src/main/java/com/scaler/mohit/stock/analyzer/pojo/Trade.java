package com.scaler.mohit.stock.analyzer.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author mohit@interviewbit.com on 09/11/20
 **/
@Data
public class Trade {
    public static enum TradeType {
        BUY,
        SELL
    }

    private String symbol;
    private int quantity;
    private TradeType tradeType;
    private LocalDate purchaseDate;
}
