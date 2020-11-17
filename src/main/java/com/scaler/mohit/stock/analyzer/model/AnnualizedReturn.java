package com.scaler.mohit.stock.analyzer.model;

import lombok.Data;

/**
 * @author mohit@interviewbit.com on 09/11/20
 **/
@Data
public class AnnualizedReturn {
    private final String symbol;
    private final Double annualizedReturn;
    private final Double totalReturns;
}
