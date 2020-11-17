package com.scaler.mohit.stockanalyzerweb;

import com.scaler.mohit.stock.analyzer.client.StockAnalyzerClient;
import com.scaler.mohit.stock.analyzer.model.AnnualizedReturn;
import com.scaler.mohit.stock.analyzer.model.Trade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mohit@interviewbit.com on 17/11/20
 **/
@RestController
public class StockAnalyzerController {
    
    @PostMapping("/")
    public List<AnnualizedReturn> getAnnualizedReturns(@RequestBody List<Trade> trades) {
        StockAnalyzerClient stockAnalyzerClient = new StockAnalyzerClient();
        return stockAnalyzerClient.getAnnualisedReturnsOnStocks(trades, "2019-12-12");
    }
}
