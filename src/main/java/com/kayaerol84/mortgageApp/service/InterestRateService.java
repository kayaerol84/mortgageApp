package com.kayaerol84.mortgageApp.service;

import java.util.List;

import com.kayaerol84.mortgageApp.model.exception.InterestRateNotFoundException;
import org.springframework.stereotype.Service;

import com.kayaerol84.mortgageApp.model.MortgageRate;
import com.kayaerol84.mortgageApp.repository.InterestRateRepository;

@Service
public class InterestRateService {
	private final InterestRateRepository interestRateRepository;

	public InterestRateService(InterestRateRepository interestRateRepository) {
		this.interestRateRepository = interestRateRepository;
	}

	public List<MortgageRate> getInterestRates() {

		return interestRateRepository.findAll();
	}

	public MortgageRate getByMaturityPeriod(int maturityPeriod) {

		return interestRateRepository.findByMaturityPeriod(maturityPeriod)
				.orElseThrow( ()->new InterestRateNotFoundException("Not found for: ", maturityPeriod));
	}
}


