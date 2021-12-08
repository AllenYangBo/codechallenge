package com.codechallenge;

import com.codechallenge.calculate.Calculator;
import com.codechallenge.calculate.CalculatorImpl;
import com.codechallenge.input.AnalyzeInput;
import com.codechallenge.input.AnalyzeInputImpl;
import com.codechallenge.model.Employee;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;

@Slf4j
public class ApplicationMain {

    public static void main(String[] args) throws IOException, IllegalArgumentException {
        //Create instances of dependencies

        //If you want to get input from console, please turn this on
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //This reads commands from a file
        BufferedReader reader = fileReader();
        AnalyzeInput analyzer = new AnalyzeInputImpl();
        Calculator<BigDecimal> calculator = new CalculatorImpl();

        //Init an employee instance
        Employee<BigDecimal> employee = new Employee(calculator, CalculatorImpl.divideBy12, reader, analyzer);
        //Read from stream
        try{
            employee.input();
        }catch(Exception e) {
            log.error(e.getMessage());
            return;
        }
        //calculate
        employee.calculate();
        //output to stream
        employee.display(System.out);
    }

    private static BufferedReader fileReader() throws FileNotFoundException {
        ClassLoader classLoader = ApplicationMain.class.getClassLoader();
        URL resource = classLoader.getResource("input");
        return new BufferedReader(new FileReader( resource.getFile() ));
    }
}
