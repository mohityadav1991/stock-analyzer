package com.scaler.mohit.stock.analyzer.model;

import java.time.LocalDate;

public interface Candle {
    Double getOpen();

    Double getClose();

    Double getHigh();

    Double getLow();

    LocalDate getDate();
}
