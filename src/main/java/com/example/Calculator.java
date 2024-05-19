package com.example;

public class Calculator {


    static int add(String numbers){
        int result = 0;
        String [] parts;
        if (numbers.isEmpty())
            return result;
        String delimiter = ",";

        parts = numbers.split("[\n]+");


        if (parts[0].startsWith("//")){
            delimiter= parts[0].substring(2);
            parts[0] = null;
        }

        for (String part : parts){
            try {
                String [] subParts = part.split(delimiter);
                for (String subPart : subParts){
                    result = result + Integer.parseInt(part);
                }

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return result;
    }
}
