package com.kayaerol84.mortgageApp.repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.kayaerol84.mortgageApp.model.MortgageRate;

@Repository
public class InMemoryInterestRateRepository implements InterestRateRepository {

    // Actual Interest rates retrieved from here  https://www.ing.nl/particulier/hypotheek/actuele-hypotheekrente
    private final Map<Integer, MortgageRate> mortgageRatesPerMaturityPeriod;

    public InMemoryInterestRateRepository() {
        this.mortgageRatesPerMaturityPeriod = new ConcurrentHashMap<>(
                Map.of(5, new MortgageRate(5, 3.5, Instant.now()),
                        10, new MortgageRate(10, 4.0, Instant.now()),
                        15, new MortgageRate(15, 4.5, Instant.now()),
                        20, new MortgageRate(20, 4.5, Instant.now()),
                        30, new MortgageRate(30, 5.0, Instant.now()))

        );
    }

    @Override
    public List<MortgageRate> findAll() {
        return mortgageRatesPerMaturityPeriod.values().stream().toList();
    }

    @Override
    public Optional<MortgageRate> findByMaturityPeriod(int maturityPeriod) {
        return Optional.ofNullable(mortgageRatesPerMaturityPeriod.get(maturityPeriod));
    }

    @Override
    public MortgageRate getByMaturityPeriod(int maturityPeriod) {
        return findByMaturityPeriod(maturityPeriod).orElseThrow();
    }

    @Override
    public boolean exists(int maturityPeriod) {
        return mortgageRatesPerMaturityPeriod.containsKey(maturityPeriod);
    }
}