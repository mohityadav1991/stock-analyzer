package com.scaler.mohit.stock.analyzer.pojo;

import java.time.LocalDate;

public interface Candle {
    Double getOpen();

    Double getClose();

    Double getHigh();

    Double getLow();

    LocalDate getDate();
}
