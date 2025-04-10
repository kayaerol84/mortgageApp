package com.kayaerol84.mortgageApp.service.validator;

public enum ErrorReason {
	LOAN_EXCEEDS_HOME_VALUE("Loan value exceeds the home value."),
	LOAN_EXCEEDS_4_INCOME("Loan value exceeds 4 times the income."),
	INTEREST_RATE_FOR_MATURITY_NOT_FOUND("Interest for maturity period not found.");

	ErrorReason(String s) {

	}
}
