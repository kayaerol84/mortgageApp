package com.kayaerol84.mortgageApp.service;

import com.kayaerol84.mortgageApp.model.Amount;
import com.kayaerol84.mortgageApp.model.MortgageCheck;
import com.kayaerol84.mortgageApp.model.MortgageCheckResult;
import com.kayaerol84.mortgageApp.model.MortgageRate;
import com.kayaerol84.mortgageApp.service.validator.ErrorReason;
import com.kayaerol84.mortgageApp.service.validator.MortgageCheckValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MortgageCheckServiceTest {

    @Mock
    private MortgageCheckValidator mortgageCheckValidator;

    @Mock
    private MonthlyCostCalculator monthlyCostCalculator;

    @InjectMocks
    private MortgageCheckService mortgageCheckService;

    @Test
    void shouldReturnNotFeasibleAndZeroCostsWhenValidationFails() {
        // Arrange
        var request = new MortgageCheck(new Amount(BigDecimal.valueOf(100000)), new MortgageRate(10, 1, Instant.now()), new Amount(BigDecimal.valueOf(500000)), new Amount(BigDecimal.valueOf(600000)));
        List<ErrorReason> validationErrors = Collections.singletonList(ErrorReason.LOAN_EXCEEDS_HOME_VALUE);
        when(mortgageCheckValidator.validate(request)).thenReturn(validationErrors);

        // Act
        MortgageCheckResult result = mortgageCheckService.checkMortgage(request);

        // Assert
        assertFalse(result.feasible());
        assertEquals(BigDecimal.ZERO, result.monthlyCosts().value());
    }

    @Test
    void shouldReturnFeasibleAndCostsWhenValidationPasses() {
        // Arrange
        double interestRate = 5;
        var maturityPeriod = 20;
        var now = Instant.now();
        MortgageRate mortgageRate = new MortgageRate(maturityPeriod, interestRate, now);
        MortgageCheck request = new MortgageCheck(new Amount(BigDecimal.valueOf(100000)), mortgageRate, new Amount(BigDecimal.valueOf(300000)), new Amount(BigDecimal.valueOf(400000)));
        when(mortgageCheckValidator.validate(request)).thenReturn(Collections.emptyList());

        BigDecimal expectedMonthlyCosts = new BigDecimal("1815.93");
        when(monthlyCostCalculator.calculateMonthlyPayment(request.loanValue(), BigDecimal.valueOf(interestRate), 20)).thenReturn(expectedMonthlyCosts);

        // Act
        MortgageCheckResult result = mortgageCheckService.checkMortgage(request);

        // Assert
        assertTrue(result.feasible());
        assertEquals(expectedMonthlyCosts, result.monthlyCosts().value());
    }
}