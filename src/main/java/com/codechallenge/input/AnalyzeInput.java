package com.codechallenge.input;

/**
 * This interface analyzes the input string
 * */
public interface AnalyzeInput {

    String[] analyze(String input) throws IllegalArgumentException;
    void validate(String input);
}
