package com.scaler.mohit.stock.analyzer.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author mohit@interviewbit.com on 09/11/20
 **/
@Data
public class TiingoCandle implements Candle {
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private LocalDate date;
}
