package com.kayaerol84.mortgageApp.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.kayaerol84.mortgageApp.model.Amount;

@Component
public class MonthlyCostCalculator {

    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal TWELVE = BigDecimal.valueOf(12);
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    public BigDecimal calculateMonthlyPayment(Amount loanValue, BigDecimal annualInterestRate, int maturityYears) {

        int totalPayments = maturityYears * 12;

        if (annualInterestRate.compareTo(ZERO) == 0) {
            return calculateZeroInterestPayment(loanValue, totalPayments);
        }

        BigDecimal monthlyInterestRate = calculateMonthlyInterestRate(annualInterestRate);
        BigDecimal compoundedRate = calculateCompoundedRate(monthlyInterestRate, totalPayments);

        BigDecimal numerator = loanValue.value()
                .multiply(monthlyInterestRate, MATH_CONTEXT)
                .multiply(compoundedRate, MATH_CONTEXT);

        BigDecimal denominator = compoundedRate.subtract(ONE, MATH_CONTEXT);

        return numerator
                .divide(denominator, MATH_CONTEXT)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateZeroInterestPayment(Amount loanValue, int totalPayments) {

        return loanValue.value()
                .divide(BigDecimal.valueOf(totalPayments), MATH_CONTEXT)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateMonthlyInterestRate(BigDecimal annualRate) {

        return annualRate.divide(HUNDRED, MATH_CONTEXT)
                .divide(TWELVE, MATH_CONTEXT);
    }

    private BigDecimal calculateCompoundedRate(BigDecimal monthlyRate, int periods) {

        return ONE.add(monthlyRate, MATH_CONTEXT)
                .pow(periods, MATH_CONTEXT);
    }
}
