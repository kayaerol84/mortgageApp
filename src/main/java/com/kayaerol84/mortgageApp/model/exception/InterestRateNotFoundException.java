package com.kayaerol84.mortgageApp.model.exception;

public class InterestRateNotFoundException extends RuntimeException {
    public InterestRateNotFoundException(String message, int maturityPeriod) {
        super(message+maturityPeriod);
    }
}