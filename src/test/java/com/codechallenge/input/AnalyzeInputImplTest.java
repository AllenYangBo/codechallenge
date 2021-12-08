package com.codechallenge.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyzeInputImplTest {

    final AnalyzeInputImpl analyzeInputImpl = new AnalyzeInputImpl();
    final String input_1 = "GenerateMonthlyPayslip \"Mary Song\"  60000";
    final String wrong_formatted_input_1 = "GenerateMonthlyPayslip\"Mary Song\"  60000";

    @Test
    void validate() {
        assertTrue(analyzeInputImpl.validate(input_1));
        assertFalse(analyzeInputImpl.validate(wrong_formatted_input_1) );
    }

    @Test
    void analyze() {
        String[] inputStr = analyzeInputImpl.analyze(input_1);
        assertTrue(inputStr[1].equals("Mary Song"));
        assertTrue(inputStr[2].equals("60000"));
    }

    @Test
    void analyze_invalidInput() {
        assertThrows(IllegalArgumentException.class, ()->{
            analyzeInputImpl.analyze(wrong_formatted_input_1);
        });
    }
}
