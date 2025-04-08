package com.kayaerol84.mortgageApp.service.validator;

import java.util.ArrayList;
import java.util.List;

import com.kayaerol84.mortgageApp.model.MortgageCheck;
import org.springframework.stereotype.Component;

import com.kayaerol84.mortgageApp.api.dto.MortgageCheckRequest;

@Component
public class MortgageCheckValidator {

	private final List<ValidationRule> validationRules;

	public MortgageCheckValidator(List<ValidationRule> validationRules) {
		this.validationRules = validationRules;
	}

	public List<ErrorReason> validate(MortgageCheck request) {
		List<ErrorReason> errors = new ArrayList<>();
		for (ValidationRule rule : validationRules) {
			errors.addAll(rule.validate(request));
		}
		return errors;
	}
}
