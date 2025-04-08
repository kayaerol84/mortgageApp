package com.kayaerol84.mortgageApp.repository;

import java.util.List;
import java.util.Optional;

import com.kayaerol84.mortgageApp.model.MortgageRate;

public interface InterestRateRepository {

	List<MortgageRate> findAll();
	Optional<MortgageRate> findByMaturityPeriod(int maturityPeriod);
	MortgageRate getByMaturityPeriod(int maturityPeriod);
	boolean exists(int maturityPeriod);
}
