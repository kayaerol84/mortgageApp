package com.kayaerol84.mortgageApp.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.kayaerol84.mortgageApp.model.Amount;

class MonthlyCostCalculatorTest {
	private MonthlyCostCalculator monthlyCostCalculator = new MonthlyCostCalculator();

	@ParameterizedTest(name = "{0}")
	@CsvSource({
			"Common scenario, 300000, 3.6, 30, 1363.94",
			"Smaller loan higher rate shorter term, 100000, 6.0, 10, 1110.21",
			"Larger loan lower rate longer term, 500000, 3.0, 30, 2108.02",
			"Very small loan, 10000, 5.0, 5, 188.71",
			"Zero interest rate, 1000000, 0.0, 15, 5555.56",
			"Another realistic scenario, 250000, 7.25, 25, 1807.02"
	})
	void testCalculateMonthlyPayment(
			String test,
			String loanValueStr,
			String interestRateStr,
			int maturityPeriodYears,
			String expectedMonthlyPaymentStr
	) {
		// Arrange
		Amount loanValue = new Amount(new BigDecimal(loanValueStr));
		BigDecimal interestRate = new BigDecimal(interestRateStr);
		BigDecimal expectedMonthlyPayment = new BigDecimal(expectedMonthlyPaymentStr);

		// Act
		BigDecimal actualMonthlyPayment = monthlyCostCalculator.calculateMonthlyPayment(loanValue, interestRate, maturityPeriodYears);

		// Assert
		assertEquals(expectedMonthlyPayment, actualMonthlyPayment);
	}
}