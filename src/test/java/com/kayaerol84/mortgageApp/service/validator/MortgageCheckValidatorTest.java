package com.kayaerol84.mortgageApp.service.validator;

import com.kayaerol84.mortgageApp.model.MortgageCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MortgageCheckValidatorTest {

	@Mock
	private ValidationRule rule1;

	@Mock
	private ValidationRule rule2;

	@InjectMocks
	private MortgageCheckValidator validator;

	@BeforeEach
	void setUp() {
		validator = new MortgageCheckValidator(List.of(rule1, rule2));
	}

	@Test
	void shouldReturnCombinedErrorsFromAllValidationRules() {
		MortgageCheck request = mock(MortgageCheck.class);

		// Stub each rule to return specific errors
		when(rule1.validate(request)).thenReturn(List.of(ErrorReason.LOAN_EXCEEDS_4_INCOME));
		when(rule2.validate(request)).thenReturn(List.of(ErrorReason.LOAN_EXCEEDS_HOME_VALUE));

		// Act
		List<ErrorReason> result = validator.validate(request);

		// Assert
		assertThat(result).containsExactlyInAnyOrder(
				ErrorReason.LOAN_EXCEEDS_4_INCOME,
				ErrorReason.LOAN_EXCEEDS_HOME_VALUE
		);
	}

	@Test
	void shouldReturnEmptyListWhenNoValidationErrors() {
		MortgageCheck request = mock(MortgageCheck.class);

		when(rule1.validate(request)).thenReturn(Collections.emptyList());
		when(rule2.validate(request)).thenReturn(Collections.emptyList());

		List<ErrorReason> result = validator.validate(request);

		assertThat(result).isEmpty();
	}

}