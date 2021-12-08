package com.codechallenge.calculate;

import java.math.BigDecimal;

/**
 * This interface calculates the incomes
 * */
public interface Calculator<T extends Number> {
    /**
     * Calculates the monthly net income
     * */
    T monthlyIncome(T grossAnnualIncome, T annualTax);
    /**
     * Calculates the annual tax
     * */
    T annualTax(T grossAnnualIncome);

    /**
     * Convert from string to a specific number type
     * */
    T convert(String number);
}
