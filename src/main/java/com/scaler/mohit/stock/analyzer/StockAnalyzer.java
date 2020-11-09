package com.scaler.mohit.stock.analyzer;

import com.scaler.mohit.stock.analyzer.pojo.AnnualizedReturn;

import java.util.Collections;
import java.util.List;

/**
 * @author mohit@interviewbit.com on 09/11/20
 **/
public class StockAnalyzer {

    /*
     *  TODO: Complete this method to read the file and return list of all stocks.
     *   @Param: Filename (placed in resources folder)
     *   @Returns: List of stocks present in the file
     */
    public static List<String> readStocksFromFile(String fileName) {
        return Collections.emptyList();
    }

    /*
     *  TODO: Complete this method to read the file and return list of all
     *   stocks in asc order of prices on the given date.
     *   @Param: Filename (placed in resources folder)
     *   @Returns: List of stocks present in the file
     *
     *  NOTE: Please create a free account on Tiingo
     *      (https://api.tiingo.com/documentation/end-of-day)
     *      and use their API to find out the closing price
     */
    public static List<String> getStockSymbolsInAscPrice(String fileName, String endDate) {
        return Collections.emptyList();
    }

    /*
     *  TODO: Complete this method to read the file and return
     *   Annualized Return on each stock present in the file. Sort in desc order of gain.
     *   @Param: Filename (placed in resources folder)
     *   @Returns: List of stocks present in the file
     *
     *  Read about annualised return here: https://scripbox.com/mf/annualized-return/
     *  NOTE: Use Tiingo API for stock prices on specific date.
     *      If the price is not available on given endDate, consider price for a day before endDate.
     */
    public static List<AnnualizedReturn> getAnnualisedReturnsOnStocks(String fileName, String endDate) {
        return Collections.emptyList();
    }

    public static void main(String[] args) {

    }
}
