package com.codechallenge.calculate;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Calculate the monthly income according to the gross income
 * */
@Slf4j
public class CalculatorImpl implements Calculator<BigDecimal> {

    public final static BigDecimal income_180001 = new BigDecimal(180001);
    public final static BigDecimal income_180000 = new BigDecimal(180000);
    public final static BigDecimal income_80001 = new BigDecimal(80001);
    public final static BigDecimal income_80000 = new BigDecimal(80000);
    public final static BigDecimal income_40001 = new BigDecimal(40001);
    public final static BigDecimal income_40000 = new BigDecimal(40000);
    public final static BigDecimal income_20001 = new BigDecimal(20001);
    public final static BigDecimal income_20000 = new BigDecimal(20000);
    public final static Map<BigDecimal, Double> taxRate = new HashMap<>();

    public CalculatorImpl() {
        taxRate.put(BigDecimal.ZERO, 0d);
        taxRate.put(income_20001, 0.1d);
        taxRate.put(income_40001, 0.2d);
        taxRate.put(income_80001, 0.3d);
        taxRate.put(income_180001, 0.4d);
    }
    /**
     * Due to the precision problem in divide operation in Big decimal, this divide operation is shared in this
     * big decimal implementation
     * */
    public final static Function<BigDecimal, BigDecimal> divideBy12= (a)->
            a.divide(BigDecimal.valueOf(12),2, RoundingMode.HALF_UP);

    /**
     * This calculates the annual tax for the gross income
     * @param grossAnnualIncome gross annual income
     * @return the total tax for the gross income
     * */
    public BigDecimal annualTax(@NonNull BigDecimal grossAnnualIncome) {
        log.info("Start to calculate total tax for gross income {}", grossAnnualIncome);
        Map<BigDecimal, BigDecimal> segment = segment(grossAnnualIncome);
        return segment.entrySet().stream().map(entry-> {
            Double taxRate = taxRate(entry.getKey());
            entry.setValue(BigDecimal.valueOf(taxRate).multiply(entry.getValue()));
            return entry; }
        ).map( entry-> entry.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal convert(String number) {
        return new BigDecimal(number.toString());
    }

    /**
     * This segments the income into different tax range
     * @param grossAnnualIncome gross annual income
     * @return key - value map, key is the minimum income for its tax range, value is the income part that falls into this range
     * */
    protected Map<BigDecimal, BigDecimal> segment(@NonNull BigDecimal grossAnnualIncome) throws IllegalArgumentException {
        if(grossAnnualIncome.compareTo(BigDecimal.ZERO)<0) {
            throw new IllegalArgumentException();
        }
        log.info("Segment the income into different tax ranges for gross income {}", grossAnnualIncome);
        Map<BigDecimal, BigDecimal> incomesSegment = new HashMap<>();

        BiFunction<BigDecimal, BigDecimal, BigDecimal> segment = (range, grossIncome) -> {
            if(grossIncome.compareTo(range)>=0) {
                incomesSegment.put(range, grossIncome.subtract(range).add(BigDecimal.ONE));
                return range.subtract(BigDecimal.ONE);
            }
            return BigDecimal.ZERO;
        };

        BigDecimal[] ranges = new BigDecimal[]{income_180001, income_80001, income_40001, income_20001, BigDecimal.ZERO};
        for(BigDecimal range: ranges) {
            BigDecimal res = segment.apply(range, grossAnnualIncome);
            if(res.compareTo(BigDecimal.ZERO)>0) {
                grossAnnualIncome = res;
            }
        }

        return incomesSegment;
    }

    /**
     * Calculates monthly income for a gross annuall income
     * @param grossAnnualIncome gross annual income
     * @return monthly tax
     * */
    @Override
    public BigDecimal monthlyIncome(@NonNull BigDecimal grossAnnualIncome, @NonNull BigDecimal annualTax) {
        if(grossAnnualIncome.compareTo(BigDecimal.ZERO)<0 || annualTax.compareTo(BigDecimal.ZERO)<0) {
            log.error("Input {} or {} should be bigger than 0", grossAnnualIncome, annualTax);
            throw new IllegalArgumentException();
        }
        log.info("Get the monthly income for gross income {}", grossAnnualIncome);
        BigDecimal netIncome = grossAnnualIncome.subtract(annualTax);
        log.info("Net income is {}", netIncome);
        return divideBy12.apply(netIncome);
    }

    /**
     * Determin the tax rate by checking which income range it belongs to
     * @param grossIncome the amount of gross income
     * @return tax rate corresponding to the gross income
     * */
    protected Double taxRate(@NonNull BigDecimal grossIncome) throws IllegalArgumentException {
         Optional<Map.Entry<BigDecimal, Double>> rateEntry = taxRate.entrySet().stream().filter(entry-> entry.getKey().compareTo(grossIncome)<=0 )
                .max( (a,b) -> a.getKey().compareTo(b.getKey())<0?-1:(a.getKey().compareTo(b.getKey())==0?0:1) );
         if(rateEntry.isEmpty()) {
             log.info("Parameter of gross income {} is wrong", grossIncome);
             throw new IllegalArgumentException();
         }
         return rateEntry.get().getValue();
    }
}
