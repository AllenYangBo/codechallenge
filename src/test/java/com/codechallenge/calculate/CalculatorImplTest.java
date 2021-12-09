package com.codechallenge.calculate;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorImplTest {

    final CalculatorImpl calculator = new CalculatorImpl();

    @Test
    void taxRate() {
        assertEquals(calculator.taxRate(BigDecimal.valueOf(20000)), 0d) ;
        assertEquals(calculator.taxRate(BigDecimal.valueOf(20001)), 0.1d) ;
        assertEquals(calculator.taxRate(BigDecimal.valueOf(40000)), 0.1d) ;
        assertEquals(calculator.taxRate(BigDecimal.valueOf(40001)), 0.2d) ;
        assertEquals(calculator.taxRate(BigDecimal.valueOf(80000)), 0.2d) ;
        assertEquals(calculator.taxRate(BigDecimal.valueOf(80001)), 0.3d) ;
        assertEquals(calculator.taxRate(BigDecimal.valueOf(180000)), 0.3d) ;
        assertEquals(calculator.taxRate(BigDecimal.valueOf(200001)), 0.4d) ;
    }

    @Test
    void taxRate_invalidInput() {
        assertThrows(NullPointerException.class, ()->{
            calculator.taxRate(null);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            calculator.taxRate(BigDecimal.valueOf(-10));
        } );
    }

    @Test
    void segment() {
        Map<BigDecimal, BigDecimal> map = calculator.segment(BigDecimal.valueOf(20000));
        assertEquals(map.size(), 1);
        assertTrue(map.containsKey(BigDecimal.ZERO));

        map = calculator.segment(BigDecimal.valueOf(40000));
        assertEquals(map.size(), 2);
        assertTrue(map.containsKey(BigDecimal.ZERO));
        assertTrue(map.containsKey(CalculatorImpl.income_20001));

        map = calculator.segment(BigDecimal.valueOf(80000));
        assertEquals(map.size(), 3);
        assertTrue(map.containsKey(BigDecimal.ZERO));
        assertTrue(map.containsKey(CalculatorImpl.income_20001));
        assertTrue(map.containsKey(CalculatorImpl.income_40001));

        map = calculator.segment(BigDecimal.valueOf(180000));
        assertEquals(map.size(), 4);
        assertTrue(map.containsKey(BigDecimal.ZERO));
        assertTrue(map.containsKey(CalculatorImpl.income_20001));
        assertTrue(map.containsKey(CalculatorImpl.income_40001));
        assertTrue(map.containsKey(CalculatorImpl.income_80001));

        map = calculator.segment(BigDecimal.valueOf(180001));
        assertEquals(map.size(), 5);
        assertTrue(map.containsKey(BigDecimal.ZERO));
        assertTrue(map.containsKey(CalculatorImpl.income_20001));
        assertTrue(map.containsKey(CalculatorImpl.income_40001));
        assertTrue(map.containsKey(CalculatorImpl.income_80001));
        assertTrue(map.containsKey(CalculatorImpl.income_180001));
    }

    @Test
    void segment_invalidInput() {
        assertThrows(NullPointerException.class, ()->{
            calculator.segment(null);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            calculator.segment(BigDecimal.valueOf(-10));
        });
    }

    @Test
    void annualTax() {
        BigDecimal totalTax = calculator.annualTax(BigDecimal.valueOf(20000));
        assertEquals(totalTax.doubleValue(),0d);

        totalTax = calculator.annualTax(BigDecimal.valueOf(60000));
        assertEquals(totalTax.doubleValue(),6000d);

        totalTax = calculator.annualTax(BigDecimal.valueOf(80000));
        assertEquals(totalTax.doubleValue(),10000d);

        totalTax = calculator.annualTax(BigDecimal.valueOf(180000));
        assertEquals(totalTax.doubleValue(),40000d);
    }

    @Test
    void annualTax_invalidInputs() {
        assertThrows(NullPointerException.class, ()->{
            calculator.annualTax(null);
        });
        assertThrows(IllegalArgumentException.class, ()->{
            calculator.annualTax(BigDecimal.valueOf(-10));
        });
    }

    @Test
    void monthlyIncome() {
        BigDecimal annualTax = calculator.annualTax(BigDecimal.valueOf(20000) );
        BigDecimal monthlyIncome = calculator.monthlyIncome(BigDecimal.valueOf(20000), annualTax);
        assertEquals(monthlyIncome, CalculatorImpl.divideBy12.apply(BigDecimal.valueOf(20000)));
        monthlyIncome = calculator.monthlyIncome(BigDecimal.ZERO, BigDecimal.ZERO);
        assertEquals(monthlyIncome, CalculatorImpl.divideBy12.apply(BigDecimal.valueOf(0)));
    }

    @Test
    void monthlyIncome_invalidInputs() {
        assertThrows(NullPointerException.class, ()-> {
            calculator.monthlyIncome(null, null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            calculator.monthlyIncome(BigDecimal.valueOf(-10), BigDecimal.valueOf(-10));
        });
    }

}
