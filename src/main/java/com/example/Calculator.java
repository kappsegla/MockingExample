package com.example;

public class Calculator {


    static int add(String numbers){
        int result = 0;
        String [] parts;
        if (numbers.equals(""))
            return result;
        else {
            parts = numbers.split(",");
            for (String part : parts){
                try {
                    result = result + Integer.parseInt(part);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return result;
    }
}
