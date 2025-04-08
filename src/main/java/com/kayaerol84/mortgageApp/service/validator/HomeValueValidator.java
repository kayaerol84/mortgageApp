package com.kayaerol84.mortgageApp.service.validator;

import static com.kayaerol84.mortgageApp.service.validator.ErrorReason.LOAN_EXCEEDS_HOME_VALUE;

import com.kayaerol84.mortgageApp.model.MortgageCheck;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class HomeValueValidator implements ValidationRule {

	@Override
	public List<ErrorReason> validate(MortgageCheck request) {
		List<ErrorReason> errors = new ArrayList<>();

		if (request.loanValue().compareTo(request.homeValue()) > 0) {
			errors.add(LOAN_EXCEEDS_HOME_VALUE);
		}

		return errors;
	}
}
