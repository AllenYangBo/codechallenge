package com.codechallenge.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnalyzeInputImplTest {

    final AnalyzeInputImpl analyzeInputImpl = new AnalyzeInputImpl();
    final String input_1 = "GenerateMonthlyPayslip \"Mary Song\"  60000";
    final String wrong_formatted_input_1 = "GenerateMonthlyPayslip\"Mary Song\"  60000";

    @Test
    void validate() {
        analyzeInputImpl.validate(input_1);
        assertThrows(IllegalArgumentException.class, ()->{
            analyzeInputImpl.validate(wrong_formatted_input_1);
        } );
    }

    @Test
    void analyze() {
        String[] inputStr = analyzeInputImpl.analyze(input_1);
        assertEquals(inputStr[1], "Mary Song");
        assertEquals(inputStr[2], "60000");
    }

    @Test
    void analyze_invalidInput() {
        assertThrows(IllegalArgumentException.class, ()->{
            analyzeInputImpl.analyze(wrong_formatted_input_1);
        });
    }
}
