package com.kayaerol84.mortgageApp.repository;

import com.kayaerol84.mortgageApp.model.MortgageRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryInterestRateRepositoryTest {

    private InMemoryInterestRateRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryInterestRateRepository();
    }

    @Test
    void findAllShouldReturnAllMortgageRates() {
        // Act
        List<MortgageRate> allRates = repository.findAll();

        // Assert
        assertEquals(5, allRates.size());
        assertTrue(allRates.stream().anyMatch(rate -> rate.maturityPeriod() == 5));
        assertTrue(allRates.stream().anyMatch(rate -> rate.maturityPeriod() == 10));
        assertTrue(allRates.stream().anyMatch(rate -> rate.maturityPeriod() == 15));
        assertTrue(allRates.stream().anyMatch(rate -> rate.maturityPeriod() == 20));
        assertTrue(allRates.stream().anyMatch(rate -> rate.maturityPeriod() == 30));
    }

    @Test
    void findByMaturityPeriodShouldReturnOptionalWithMortgageRateWhenMaturityPeriodIsValid() {
        // Act
        Optional<MortgageRate> foundRate = repository.findByMaturityPeriod(10);

        // Assert
        assertTrue(foundRate.isPresent());
        assertEquals(10, foundRate.get().maturityPeriod());
        assertEquals(4.0, foundRate.get().interestRate());
    }

    @Test
    void findByMaturityPeriodShouldReturnEmptyOptionalWhenNoMaturityPeriodFound() {
        // Act
        Optional<MortgageRate> foundRate = repository.findByMaturityPeriod(25);

        // Assert
        assertFalse(foundRate.isPresent());
    }

    @Test
    void getByMaturityPeriodShouldReturnMortgageRateWhenExists() {
        // Act
        MortgageRate foundRate = repository.getByMaturityPeriod(15);

        // Assert
        assertEquals(15, foundRate.maturityPeriod());
        assertEquals(4.5, foundRate.interestRate());
    }

    @Test
    void getByMaturityPeriodShouldThrowsNoSuchElementExceptionWhenNoMaturityPeriodFound() {
        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> repository.getByMaturityPeriod(25));
    }

    @Test
    void existsShouldReturnTrueWhenMaturityPeriodExists() {
        // Act
        boolean exists = repository.exists(20);

        // Assert
        assertTrue(exists);
    }

    @Test
    void existsShouldReturnsFalseWhenMaturityPeriodNotExists() {
        // Act
        boolean exists = repository.exists(40);

        // Assert
        assertFalse(exists);
    }

}