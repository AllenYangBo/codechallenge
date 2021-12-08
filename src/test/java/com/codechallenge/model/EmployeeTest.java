package com.codechallenge.model;

import com.codechallenge.calculate.Calculator;
import com.codechallenge.calculate.CalculatorImpl;
import com.codechallenge.input.AnalyzeInput;
import com.codechallenge.input.AnalyzeInputImpl;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmployeeTest {

    static URL resource = EmployeeTest.class.getClassLoader().getResource("input");
    static URL resource_errorInput = EmployeeTest.class.getClassLoader().getResource("invalidInput");
    AnalyzeInput analyzer = new AnalyzeInputImpl();
    Calculator<BigDecimal> calculator = new CalculatorImpl();

    @Test
    void input() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader( resource.getFile() ));
        Employee<BigDecimal> employee = new Employee(calculator, CalculatorImpl.divideBy12, reader, analyzer);
        employee.input();
        assertEquals(employee.getName(), "Mary Song for Test");
        assertEquals(employee.getGrossAnnualIncome(), new BigDecimal("80000") );
    }

    @Test
    void input_invalidInput() throws IOException {
        BufferedReader reader_errorInput = new BufferedReader(new FileReader( resource_errorInput.getFile() ));
        Employee<BigDecimal> employee = new Employee(calculator, CalculatorImpl.divideBy12, reader_errorInput, analyzer);
        assertThrows(IllegalArgumentException.class, ()->{
            employee.input();
        } );
    }


    @Test
    void calculate() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader( resource.getFile() ));
        Employee<BigDecimal> employee = new Employee(calculator, CalculatorImpl.divideBy12, reader, analyzer);
        employee.input();
        employee.calculate();
        assertEquals(employee.getNetMonthlyIncome(), BigDecimal.valueOf(5833.33) );
    }

}
