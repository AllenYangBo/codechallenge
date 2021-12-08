package com.codechallenge.input;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AnalyzeInputImpl implements AnalyzeInput{
    public final static Pattern p = Pattern.compile("[a-zA-Z]+[\\s]+\".*\"[\\s]+[\\d]+");

    @Override
    public String[] analyze(@NonNull String input) throws IllegalArgumentException {
        if(!validate(input.trim())) {
            log.error("Input {} is wrongly formatted", input);
            throw new IllegalArgumentException("Invalid input");
        }

        String[] res = input.split("\"");
        for(int i=0;i<res.length;i++) {
            res[i] =  res[i].trim();
        }
        return res;
    }

    @Override
    public boolean validate(String input) {
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
