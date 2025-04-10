package com.kayaerol84.mortgageApp.model;

import java.time.Instant;

public record MortgageRate(int maturityPeriod, double interestRate, Instant lastUpdate) {}

