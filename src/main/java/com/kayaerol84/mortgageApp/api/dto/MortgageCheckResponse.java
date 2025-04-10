package com.kayaerol84.mortgageApp.api.dto;

import com.kayaerol84.mortgageApp.model.MortgageCheckResult;

import java.math.BigDecimal;

public record MortgageCheckResponse(boolean feasible, BigDecimal monthlyCosts, String currency) {
    public static MortgageCheckResponse from(MortgageCheckResult mortgageCheckResult) {
        return new MortgageCheckResponse(
                mortgageCheckResult.feasible(),
                mortgageCheckResult.monthlyCosts().value(),
                mortgageCheckResult.monthlyCosts().currency());
    }
}
