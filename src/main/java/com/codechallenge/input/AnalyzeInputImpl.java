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
        validate(input.trim());
        String[] res = input.split("\"");
        for(int i=0;i<res.length;i++) {
            res[i] =  res[i].trim();
        }
        return res;
    }

    @Override
    public void validate(String input) throws IllegalArgumentException{
        Matcher m = p.matcher(input);
        if(!m.matches()) {
            log.error("Input {} is wrongly formatted", input);
            throw new IllegalArgumentException("Input "+input+" is wrongly formatted");
        }
    }
}
