package com.kayaerol84.mortgageApp.model;

public record MortgageCheck(Amount income, MortgageRate mortgageRate, Amount loanValue, Amount homeValue) {
    public String getCurrency() {
        return income.currency();
    }
}
