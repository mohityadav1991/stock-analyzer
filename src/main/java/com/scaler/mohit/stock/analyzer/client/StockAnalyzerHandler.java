package com.scaler.mohit.stock.analyzer.client;

import com.scaler.mohit.stock.analyzer.model.AnnualizedReturn;
import com.scaler.mohit.stock.analyzer.model.Trade;
import com.scaler.mohit.stock.analyzer.service.StockPriceService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author mohit@interviewbit.com on 17/11/20
 **/
public class StockAnalyzerHandler {
    private final StockPriceService stockPriceService;

    public StockAnalyzerHandler(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    public List<AnnualizedReturn> getAnnualisedReturnsOnStocks(List<Trade> trades, String endDate) {
        List<AnnualizedReturn> returns = new ArrayList<>();
        trades.forEach(trade -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(endDate, formatter);
            double buyPrice = stockPriceService.fetchStockPrice(trade, null).getOpen();
            double sellPrice = stockPriceService.fetchStockPrice(trade, date).getClose();
            AnnualizedReturn annualizedReturn = stockPriceService.calculateAnnualizedReturns(date, trade,
                    buyPrice, sellPrice);
            returns.add(annualizedReturn);
        });
        Collections.sort(returns, Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed());
        return returns;
    }
}
