package com.scaler.mohit.stock.analyzer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.scaler.mohit.stock.analyzer.pojo.AnnualizedReturn;
import com.scaler.mohit.stock.analyzer.pojo.Candle;
import com.scaler.mohit.stock.analyzer.pojo.TiingoCandle;
import com.scaler.mohit.stock.analyzer.pojo.Trade;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mohit@interviewbit.com on 09/11/20
 **/
public class StockAnalyzer {

    /*
     * TODO:
     *  1. getFile => getFileFromResource to Utils
     *  2. fetchPriceFromTiingo => TiingoService implements StockPricingService
     *  3. calculateAnnualizedReturns => BL layer
     *  4. Interface to fetchPrice
     *  5. Factory Pattern to fetch actual implementation
     *  6. Dependency Injection
     */

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static List<String> readStocksFromFile(String fileName) throws URISyntaxException, IOException {
        File file = getFile(fileName);
        Trade[] trades = MAPPER.readValue(file, Trade[].class);
        List<String> symbols = Arrays.stream(trades).map(Trade::getSymbol).collect(Collectors.toList());
        return symbols;
    }

    public static File getFile(String fileName) throws URISyntaxException {
        return Paths.get(
                Thread.currentThread().getContextClassLoader().getResource(fileName).toURI()).toFile();
    }

    public static List<String> getStockSymbolsInAscPrice(String fileName, String endDate) throws IOException, URISyntaxException {
        File file = getFile(fileName);
        Trade[] trades = MAPPER.readValue(file, Trade[].class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(endDate, formatter);
        Map<Double, String> priceMap = new TreeMap<>();
        Arrays.stream(trades).forEach(trade -> {
            if (date.isBefore(trade.getPurchaseDate())) {
                throw new RuntimeException();
            }
            String url = String.format("https://api.tiingo.com/tiingo/daily/%s/prices?startDate=%s&endDate=%s&token=%s",
                    trade.getSymbol(),
                    endDate,
                    endDate,
                    "03d1619c795c3287bc7c27db671ac4264f3b5410&");
            TiingoCandle[] tiingoCandles = REST_TEMPLATE.getForObject(url, TiingoCandle[].class);
            double price;
            if (tiingoCandles == null || tiingoCandles.length == 0) {
                throw new RuntimeException();
            } else {
                price = tiingoCandles[0].getClose();
            }
            priceMap.put(price, trade.getSymbol());
        });
        List<String> stocksByValueAsc = priceMap.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return stocksByValueAsc;
    }

    public static List<AnnualizedReturn> getAnnualisedReturnsOnStocks(String fileName, String endDate)
            throws URISyntaxException, IOException {
        List<AnnualizedReturn> returns = new ArrayList<>();
        Trade[] trades = MAPPER.readValue(getFile(fileName), Trade[].class);
        Arrays.stream(trades).forEach(trade -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(endDate, formatter);
            double buyPrice = fetchPriceFromTiingo(trade, null).getOpen();
            double sellPrice = fetchPriceFromTiingo(trade, date).getClose();
            AnnualizedReturn annualizedReturn = calculateAnnualizedReturns(date, trade,
                    buyPrice, sellPrice);
            returns.add(annualizedReturn);
        });
        Collections.sort(returns, Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed());
        return returns;
    }

    private static Candle fetchPriceFromTiingo(Trade portfolioTrade,
                                               LocalDate endDate) {
        Candle candle = null;
        LocalDate date;
        if (endDate == null) {
            date = portfolioTrade.getPurchaseDate();
        } else {
            date = endDate;
        }
        while (candle == null) {
            String url = String.format("https://api.tiingo.com/tiingo/daily/%s/prices"
                            + "?startDate=%s&endDate=%s&token=03d1619c795c3287bc7c27db671ac4264f3b5410&",
                    portfolioTrade.getSymbol(), date.toString(), date.toString());
            Candle[] candles = REST_TEMPLATE.getForObject(url, Candle[].class);
            if (candles != null && candles.length > 0) {
                candle = candles[0];
            }
            date = date.minus(1, ChronoUnit.DAYS);
        }
        return candle;
    }

    private static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
                                                               Trade trade,
                                                               Double buyPrice,
                                                               Double sellPrice) {
        Double totalReturn = (sellPrice - buyPrice) / buyPrice;
        long days = ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);
        Double annualizedReturn = Math.pow((1 + totalReturn), (365.0 / days)) - 1;
        return new AnnualizedReturn(trade.getSymbol(), annualizedReturn, totalReturn);
    }
}
