package com.scaler.mohit.stock.analyzer.client;

import com.scaler.mohit.stock.analyzer.config.StockAnalyzerConfig;
import com.scaler.mohit.stock.analyzer.model.AnnualizedReturn;
import com.scaler.mohit.stock.analyzer.model.Trade;

import java.util.List;

/**
 * @author mohit@interviewbit.com on 17/11/20
 **/
public class StockAnalyzerClient {

    private final StockAnalyzerHandler stockAnalyzerHandler;

    public StockAnalyzerClient() {
        StockAnalyzerConfig stockAnalyzerConfig = StockAnalyzerConfig.getInstance();
        this.stockAnalyzerHandler = stockAnalyzerConfig.getStockAnalyzerHandler();
    }

    public List<AnnualizedReturn> getAnnualisedReturnsOnStocks(List<Trade> trades, String endDate) {
        return stockAnalyzerHandler.getAnnualisedReturnsOnStocks(trades, endDate);
    }
}
