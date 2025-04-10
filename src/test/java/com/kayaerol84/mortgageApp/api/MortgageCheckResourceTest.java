package com.kayaerol84.mortgageApp.api;

import com.kayaerol84.mortgageApp.api.adapter.MortgageCheckRequestAdapter;
import com.kayaerol84.mortgageApp.api.dto.MortgageCheckRequest;
import com.kayaerol84.mortgageApp.api.dto.MortgageCheckResponse;
import com.kayaerol84.mortgageApp.model.Amount;
import com.kayaerol84.mortgageApp.model.MortgageCheck;
import com.kayaerol84.mortgageApp.model.MortgageCheckResult;
import com.kayaerol84.mortgageApp.model.MortgageRate;
import com.kayaerol84.mortgageApp.model.exception.InterestRateNotFoundException;
import com.kayaerol84.mortgageApp.service.InterestRateService;
import com.kayaerol84.mortgageApp.service.MortgageCheckService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MortgageCheckResourceTest {

    @Mock
    private MortgageCheckService mortgageCheckService;

    @Mock
    private InterestRateService interestRateService;

    @Mock
    private MortgageCheckRequestAdapter mortgageCheckRequestAdapter;

    @InjectMocks
    private MortgageCheckResource mortgageCheckResource;

    @Test
    void shouldReturnOkResponseWhenRequestIsValid() {
        // Arrange
        MortgageCheckRequest request = new MortgageCheckRequest(
                BigDecimal.valueOf(100000),
                10,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(400000)
        );
        MortgageRate mortgageRate = new MortgageRate(
                10,
                0.05,
                Instant.now()
        );

        when(interestRateService.getByMaturityPeriod(10)).thenReturn(mortgageRate);
        MortgageCheck mortgageCheck = new MortgageCheck(new Amount(BigDecimal.valueOf(100000)), mortgageRate, new Amount(BigDecimal.valueOf(300000)), new Amount(BigDecimal.valueOf(400000)));
        when(mortgageCheckRequestAdapter.adapt(request, mortgageRate)).thenReturn(mortgageCheck);
        when(mortgageCheckService.checkMortgage(mortgageCheck)).thenReturn(new MortgageCheckResult(true, new Amount(BigDecimal.valueOf(1500))));

        // Act
        ResponseEntity<MortgageCheckResponse> response = mortgageCheckResource.checkMortgage(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().feasible());
        assertEquals(BigDecimal.valueOf(1500), response.getBody().monthlyCosts());
        assertEquals("EUR", response.getBody().currency());
    }

    @Test
    void shouldThrowInterestRateNotFoundExceptionWhenMaturityPeriodIsInvalid() {
        // Arrange
        MortgageCheckRequest request = new MortgageCheckRequest(
                BigDecimal.valueOf(100000),
                10,
                BigDecimal.valueOf(300000),
                BigDecimal.valueOf(400000)
        );
        MortgageRate mortgageRate = new MortgageRate(
                10,
                0.05,
                Instant.now()
        );

        when(interestRateService.getByMaturityPeriod(10)).thenThrow(new InterestRateNotFoundException("Not found for: ", 10));
        // Act
        assertThrows(InterestRateNotFoundException.class, () -> mortgageCheckResource.checkMortgage(request)) ;

    }
}
