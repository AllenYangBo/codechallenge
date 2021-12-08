package com.codechallenge.input;

import com.codechallenge.model.Employee;

import java.util.List;

/**
 * This interface analyzes the input string
 * */
public interface AnalyzeInput {

    String[] analyze(String input) throws IllegalArgumentException;
    boolean validate(String input);
}
