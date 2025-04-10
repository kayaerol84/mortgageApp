package com.kayaerol84.mortgageApp.service.validator;

import java.util.List;

import com.kayaerol84.mortgageApp.model.MortgageCheck;

public interface ValidationRule {
	List<ErrorReason> validate(MortgageCheck request);
}
