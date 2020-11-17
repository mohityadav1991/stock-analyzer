package com.scaler.mohit.stock.analyzer.service;

import com.scaler.mohit.stock.analyzer.model.AnnualizedReturn;
import com.scaler.mohit.stock.analyzer.model.Candle;
import com.scaler.mohit.stock.analyzer.model.TiingoCandle;
import com.scaler.mohit.stock.analyzer.model.Trade;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author mohit@interviewbit.com on 17/11/20
 **/
public class TiingoStockPriceService implements StockPriceService {

    private final RestTemplate restTemplate;

    public TiingoStockPriceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Candle fetchStockPrice(Trade trade, LocalDate endDate) {
        Candle candle = null;
        LocalDate date;
        if (endDate == null) {
            date = trade.getPurchaseDate();
        } else {
            date = endDate;
        }
        while (candle == null) {
            String url = prepareUrl(trade, date);
            Candle[] candles = restTemplate.getForObject(url, TiingoCandle[].class);
            if (candles != null && candles.length > 0) {
                candle = candles[0];
            }
            date = date.minus(1, ChronoUnit.DAYS);
        }
        return candle;
    }

    @Override
    public AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
                                                       Trade trade,
                                                       Double buyPrice,
                                                       Double sellPrice) {
        Double totalReturn = (sellPrice - buyPrice) / buyPrice;
        long days = ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);
        Double annualizedReturn = Math.pow((1 + totalReturn), (365.0 / days)) - 1;
        return new AnnualizedReturn(trade.getSymbol(), annualizedReturn, totalReturn);
    }

    private String prepareUrl(Trade trade, LocalDate date) {
        return String.format("https://api.tiingo.com/tiingo/daily/%s/prices"
                        + "?startDate=%s&endDate=%s&token=03d1619c795c3287bc7c27db671ac4264f3b5410&",
                trade.getSymbol(), date.toString(), date.toString());
    }
}
