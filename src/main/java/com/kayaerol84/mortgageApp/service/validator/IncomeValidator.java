package com.kayaerol84.mortgageApp.service.validator;

import static com.kayaerol84.mortgageApp.service.validator.ErrorReason.LOAN_EXCEEDS_4_INCOME;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kayaerol84.mortgageApp.model.MortgageCheck;
import org.springframework.stereotype.Component;


@Component
public class IncomeValidator implements ValidationRule {

	@Override
	public List<ErrorReason> validate(MortgageCheck request) {
		List<ErrorReason> errors = new ArrayList<>();
		BigDecimal income = request.income().value();
		BigDecimal loanValue = request.loanValue().value();

		if (loanValue.compareTo(income.multiply(BigDecimal.valueOf(4))) > 0) {
			errors.add(LOAN_EXCEEDS_4_INCOME);
		}

		return errors;
	}
}
