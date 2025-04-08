package com.kayaerol84.mortgageApp.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MortgageCheckRequest(
        @NotNull
        @DecimalMin("0.01")
        BigDecimal income,
        @NotNull
        @Min(1)
        int maturityPeriod,
        @NotNull
        @DecimalMin("0.01")
        BigDecimal loanValue,
        @NotNull
        @DecimalMin("0.01")
        BigDecimal homeValue,
        String currency) {

    public MortgageCheckRequest(BigDecimal income, int maturityPeriod, BigDecimal loanValue, BigDecimal homeValue) {
        this(income, maturityPeriod, loanValue, homeValue, "EUR");
    }
}
