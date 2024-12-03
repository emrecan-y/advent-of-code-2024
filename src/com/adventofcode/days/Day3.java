package com.adventofcode.days;

import java.util.stream.Collectors;

import com.adventofcode.AbstractAdventDay;

public class Day3 extends AbstractAdventDay {

    public Day3() {
        super(3);
    }

    public static void main(String[] args) {
        Day3 day = new Day3();
        day.partOne();
        day.partTwo();
    }

    @Override
    public void partOne() {
        long multiplicationResult = super.getInput().stream().mapToLong(s -> getCorruptedMultiplicationResult(s)).sum();
        System.out.println("The multiplication result is " + multiplicationResult);
    }

    @Override
    public void partTwo() {
        // the line breaks need to be disregarded when checking,
        // so the String List needs to be concatinated
        long multiplicationResult = getCorruptedMultiplicationResultWithDoDOnt(
                super.getInput().stream().collect(Collectors.joining()));
        System.out.println("The enabled multiplication result is " + multiplicationResult);
    }

    private long getCorruptedMultiplicationResult(String s) {
        long sum = 0;
        while (true) {
            int beginIndex = s.indexOf("mul(");
            if (beginIndex == -1) {
                break;
            }
            s = s.substring(beginIndex);
            int endIndex = s.indexOf(")") + 1;
            sum += getSingleMultiplicationResult(s.substring(0, endIndex));
            s = s.substring(4);
        }
        return sum;
    }

    private long getSingleMultiplicationResult(String s) {
        int maxValidLength = "mul(123,321)".length();
        int minValidLength = "mul(1,3)".length();
        if (s.length() > maxValidLength || s.length() < minValidLength) {
            return 0;
        }
        s = s.substring(4, s.length() - 1);
        if (!s.contains(",")) {
            return 0;
        } else {
            String[] numbers = s.split(",");
            try {
                int num1 = Integer.parseInt(numbers[0]);
                int num2 = Integer.parseInt(numbers[1]);
                if (num1 <= 999 && num2 <= 999) {
                    return num1 * num2;
                }
            } catch (NumberFormatException nfe) {
                return 0;
            }
        }
        return 0;
    }

    private long getCorruptedMultiplicationResultWithDoDOnt(String s) {
        long sum = 0;
        // split the string where there is "don't()" followed by a "do()" with any
        // characters in between, so only the valid parts of the string stay
        for (String ss : s.split("don't\\(\\).*?do\\(\\)")) {
            sum += getCorruptedMultiplicationResult(ss);

        }
        return sum;
    }
}
