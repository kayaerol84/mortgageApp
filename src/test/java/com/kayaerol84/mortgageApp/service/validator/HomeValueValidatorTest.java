package com.kayaerol84.mortgageApp.service.validator;

import com.kayaerol84.mortgageApp.model.Amount;
import com.kayaerol84.mortgageApp.model.MortgageCheck;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HomeValueValidatorTest {

    private final HomeValueValidator validator = new HomeValueValidator();
    @Test
    void shouldReturnErrorWhenLoanExceeds4xIncome() {
        MortgageCheck request = mock(MortgageCheck.class);
        when(request.homeValue()).thenReturn(new Amount(BigDecimal.valueOf(10000)));
        when(request.loanValue()).thenReturn(new Amount(BigDecimal.valueOf(50000)));

        List<ErrorReason> result = validator.validate(request);

        assertThat(result).containsExactly(ErrorReason.LOAN_EXCEEDS_HOME_VALUE);
    }

    @Test
    void shouldReturnEmptyListWhenLoanIsWithinLimits() {
        MortgageCheck request = mock(MortgageCheck.class);
        when(request.homeValue()).thenReturn(new Amount(BigDecimal.valueOf(4000)));
        when(request.loanValue()).thenReturn(new Amount(BigDecimal.valueOf(3000)));

        List<ErrorReason> result = validator.validate(request);

        assertThat(result).isEmpty();
    }
}