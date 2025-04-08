package com.kayaerol84.mortgageApp.service;

import com.kayaerol84.mortgageApp.model.Amount;
import com.kayaerol84.mortgageApp.model.MortgageCheck;
import com.kayaerol84.mortgageApp.model.MortgageCheckResult;
import com.kayaerol84.mortgageApp.service.validator.ErrorReason;
import com.kayaerol84.mortgageApp.service.validator.MortgageCheckValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MortgageCheckService {

	private final MortgageCheckValidator mortgageCheckValidator;
	private final MonthlyCostCalculator monthlyCostCalculator;

	public MortgageCheckService(MortgageCheckValidator mortgageCheckValidator,
								MonthlyCostCalculator monthlyCostCalculator) {
		this.mortgageCheckValidator = mortgageCheckValidator;
		this.monthlyCostCalculator = monthlyCostCalculator;
	}

	public MortgageCheckResult checkMortgage(MortgageCheck check) {

		List<ErrorReason> businessValidationErrors = mortgageCheckValidator.validate(check);
		if (!businessValidationErrors.isEmpty()) {
			// TODO we could log/store failed checks in the next iteration
			return new MortgageCheckResult(false, new Amount(BigDecimal.ZERO, check.getCurrency()));
		}

		BigDecimal monthlyCosts = monthlyCostCalculator.calculateMonthlyPayment(
				check.loanValue(),
				BigDecimal.valueOf(check.mortgageRate().interestRate()),
				check.mortgageRate().maturityPeriod());

		return new MortgageCheckResult(true, new Amount(monthlyCosts, check.getCurrency()));
	}
}
