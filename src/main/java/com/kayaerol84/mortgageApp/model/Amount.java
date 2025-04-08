package com.kayaerol84.mortgageApp.model;

import java.math.BigDecimal;

public record Amount(BigDecimal value, String currency) {
	public Amount(BigDecimal value) {
		this(value, "EUR"); // with default currency
	}

	public int compareTo(Amount other) {
		if (!this.currency.equalsIgnoreCase(other.currency)) {
			throw new IllegalArgumentException("Cannot compare amounts with different currencies.");
		}
		return this.value.compareTo(other.value);
	}
}
