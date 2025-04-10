package com.kayaerol84.mortgageApp.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kayaerol84.mortgageApp.model.MortgageRate;
import com.kayaerol84.mortgageApp.service.InterestRateService;

@RestController
@RequestMapping("/api")
public class InterestRateResource {

	private final InterestRateService interestRateService;

	public InterestRateResource(InterestRateService interestRateService) {
		this.interestRateService = interestRateService;
	}


	@GetMapping("/interest-rates")
	public ResponseEntity<List<MortgageRate>> getInterestRates() {
		return ResponseEntity.ok(interestRateService.getInterestRates());
	}

}
