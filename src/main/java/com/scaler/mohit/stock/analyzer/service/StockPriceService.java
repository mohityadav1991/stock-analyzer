package com.scaler.mohit.stock.analyzer.service;

import com.scaler.mohit.stock.analyzer.model.AnnualizedReturn;
import com.scaler.mohit.stock.analyzer.model.Candle;
import com.scaler.mohit.stock.analyzer.model.Trade;

import java.time.LocalDate;

public interface StockPriceService {
    AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
                                                       Trade trade,
                                                       Double buyPrice,
                                                       Double sellPrice);
    Candle fetchStockPrice(Trade trade, LocalDate endDate);
}
