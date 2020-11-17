package com.scaler.mohit.stock.analyzer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.scaler.mohit.stock.analyzer.client.StockAnalyzerClient;
import com.scaler.mohit.stock.analyzer.config.StockAnalyzerConfig;
import com.scaler.mohit.stock.analyzer.model.AnnualizedReturn;
import com.scaler.mohit.stock.analyzer.model.Trade;
import com.scaler.mohit.stock.analyzer.util.AnalyzerUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mohit@interviewbit.com on 17/11/20
 **/
public class StockAnalyzerClientTest {

    private StockAnalyzerClient stockAnalyzerClient;
    private List<Trade> trades;
    private static final ObjectMapper MAPPER =  new ObjectMapper().registerModule(new JavaTimeModule());

    @Before
    public void init() throws URISyntaxException, IOException {
        StockAnalyzerConfig config = StockAnalyzerConfig.getInstance();
        stockAnalyzerClient = new StockAnalyzerClient();
        File file = AnalyzerUtils.getFileFromResources("trades_3.json");
        trades = Arrays.asList(MAPPER.readValue(file, Trade[].class));
    }

    @Test
    public void getAnnualisedReturnsOnStocksTest() {
        List<AnnualizedReturn> result = stockAnalyzerClient.getAnnualisedReturnsOnStocks(trades, "2019-12-12");
        List<String> symbols = result.stream().map(AnnualizedReturn::getSymbol)
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(new String[]{"MSFT", "CSCO", "CTS"}), symbols);
        Assert.assertEquals(0.556, result.get(0).getAnnualizedReturn(), 0.01);
        Assert.assertEquals(0.044, result.get(1).getAnnualizedReturn(), 0.01);
        Assert.assertEquals(0.025, result.get(2).getAnnualizedReturn(), 0.01);

    }
}
