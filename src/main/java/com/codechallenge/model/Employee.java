package com.codechallenge.model;

import com.codechallenge.calculate.Calculator;
import com.codechallenge.input.AnalyzeInput;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Function;

@Setter
@Getter
@Slf4j
public class Employee<T extends Number> {

    private String name;
    private T grossAnnualIncome;
    private T grossMonthlyIncome;
    private T monthlyTax;
    private T netMonthlyIncome;

    private Calculator<T> calculator;
    private Function<T, T> divide;
    private BufferedReader reader;
    private AnalyzeInput analyzer;

    public Employee(Calculator<T> calculator, Function<T, T> divide, BufferedReader reader, AnalyzeInput analyzer) {
        this.calculator = calculator;
        this.divide = divide;
        this.reader = reader;
        this.analyzer = analyzer;
    }


    public void calculate() {
        this.grossMonthlyIncome = divide.apply(grossAnnualIncome);
        T annualTax = calculator.annualTax(grossAnnualIncome);
        this.netMonthlyIncome = calculator.monthlyIncome(grossAnnualIncome, annualTax);
        this.monthlyTax = divide.apply(annualTax);
    }

    public void input() throws IOException, IllegalArgumentException {
        String line = reader.readLine();
        String[] inputStr = analyzer.analyze(line);
        this.name = inputStr[1];
        this.grossAnnualIncome = calculator.convert(inputStr[2]);
    }


    public void display(PrintStream printStream) {
        printStream.printf("Monthly Payslip for: \"%s\"\n", name);
        printStream.printf("Gross Monthly Income: $%s\n", grossMonthlyIncome);
        printStream.printf("Monthly Income Tax: $%s\n", monthlyTax);
        printStream.printf("Net Monthly Income: $%s\n", netMonthlyIncome);
    }

}
