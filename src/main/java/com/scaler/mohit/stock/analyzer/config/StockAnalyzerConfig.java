package com.scaler.mohit.stock.analyzer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.scaler.mohit.stock.analyzer.client.StockAnalyzerHandler;
import com.scaler.mohit.stock.analyzer.service.StockPriceService;
import com.scaler.mohit.stock.analyzer.service.TiingoStockPriceService;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

/**
 * @author mohit@interviewbit.com on 17/11/20
 **/
@Getter
public class StockAnalyzerConfig {
    private static StockAnalyzerConfig config;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final StockPriceService stockPriceService;
    private final StockAnalyzerHandler stockAnalyzerHandler;

    public StockAnalyzerConfig() {
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        restTemplate = new RestTemplate();
        stockPriceService = new TiingoStockPriceService(restTemplate);
        stockAnalyzerHandler = new StockAnalyzerHandler(stockPriceService);
    }

    public static StockAnalyzerConfig getInstance() {
        if(config == null) {
            config = new StockAnalyzerConfig();
        }
        return config;
    }
}
