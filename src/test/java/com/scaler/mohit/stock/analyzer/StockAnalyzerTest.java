package com.scaler.mohit.stock.analyzer;

import com.scaler.mohit.stock.analyzer.model.AnnualizedReturn;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mohit@interviewbit.com on 09/11/20
 **/
public class StockAnalyzerTest {

    // Part 1
    @Test
    public void readStocksFromFileTest() throws IOException, URISyntaxException {
        List<String> expected = Arrays.asList("AAPL", "MSFT", "GOOGL");
        List<String> actual = StockAnalyzer.readStocksFromFile("trades.json");
        Assert.assertEquals(expected, actual);
    }

    // Part 2
    @Test
    public void getStockSymbolsInAscPriceTest() throws IOException, URISyntaxException {
        List<String> expected = Arrays.asList("MSFT", "AAPL", "GOOGL");
        List<String> actual = StockAnalyzer.getStockSymbolsInAscPrice("trades.json", "2019-12-12");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getStockSymbolsInAscPriceTest_EmptyFile() throws IOException, URISyntaxException {
        List<String> expected = Arrays.asList();
        List<String> actual = StockAnalyzer.getStockSymbolsInAscPrice("trades_empty.json", "2019-12-12");
        Assert.assertEquals(expected, actual);
    }

    /*
     *  Invalid dates = records where buy date is more than end date
     */
    @Test
    public void getStockSymbolsInAscPriceTest_InvalidDates() {
        Assert.assertThrows(RuntimeException.class, () ->
                StockAnalyzer.getStockSymbolsInAscPrice("trades_invalid_dates.json", "2019-12-12"));
    }

    /*
     *  Invalid stocks symbols
     */
    @Test
    public void getStockSymbolsInAscPriceTest_InvalidStocks() {
        Assert.assertThrows(RuntimeException.class, () ->
                StockAnalyzer.getStockSymbolsInAscPrice("trades_invalid_stocks.json", "2019-12-12"));
    }

    // Part 3
    @Test
    public void getAnnualisedReturnsOnStocksTest() throws Exception {
        String filename = "trades_3.json";
        String endDate = "2019-12-12";
        List<AnnualizedReturn> result = StockAnalyzer.getAnnualisedReturnsOnStocks(filename, endDate);
        List<String> symbols = result.stream().map(AnnualizedReturn::getSymbol)
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(new String[]{"MSFT", "CSCO", "CTS"}), symbols);
        Assert.assertEquals(0.556, result.get(0).getAnnualizedReturn(), 0.01);
        Assert.assertEquals(0.044, result.get(1).getAnnualizedReturn(), 0.01);
        Assert.assertEquals(0.025, result.get(2).getAnnualizedReturn(), 0.01);
    }

    @Test
    public void getAnnualisedReturnsOnStocksTest_EmptyFile() throws Exception {
        String filename = "trades_empty.json";
        String endDate = "2019-12-12";
        List<AnnualizedReturn> result = StockAnalyzer.getAnnualisedReturnsOnStocks(filename, endDate);
        Assert.assertEquals(Arrays.asList(), result);
    }

    @Test
    public void getAnnualisedReturnsOnStocksTest_InvalidStocks() throws Exception {
        //given
        String filename = "trades_invalid_stock.json";
        //when
        Assert.assertThrows(RuntimeException.class, () -> StockAnalyzer
                .getAnnualisedReturnsOnStocks(filename, "2019-12-12"));
    }

}