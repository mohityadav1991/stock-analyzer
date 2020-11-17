package com.scaler.mohit.stock.analyzer.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.time.LocalDate;

/**
 * @author mohit@interviewbit.com on 09/11/20
 **/
@Data
@Builder
public class Trade {
    @Tolerate
    public Trade() {
    }

    public static enum TradeType {
        BUY,
        SELL
    }

    private String symbol;
    private int quantity;
    private TradeType tradeType;
    private LocalDate purchaseDate;
}
