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
        long multiplicationResult = getEnabledCorruptedMultiplicationResult(
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
            sum += resolveMultiplicationStatement(s.substring(0, endIndex));
            s = s.substring("mul(".length());
        }
        return sum;
    }

    private long getEnabledCorruptedMultiplicationResult(String s) {
        long sum = 0;
        // "don't()" followed by a "do()" with inbetween;
        // "." -> any character
        // "*" -> 0 or more matches
        // "?" -> lazy, so matches as few charactes as possible
        // so only the valid parts of the string stay after splitting
        for (String substring : s.split("don't\\(\\).*?do\\(\\)")) {
            sum += getCorruptedMultiplicationResult(substring);
        }
        return sum;
    }

    private long resolveMultiplicationStatement(String s) {
        int maxValidLength = "mul(123,321)".length();
        int minValidLength = "mul(1,3)".length();
        if (s.length() > maxValidLength || s.length() < minValidLength) {
            return 0;
        }
        s = s.substring("mul(".length(), s.length() - 1);
        if (s.contains(",")) {
            String[] numbers = s.split(",");
            return multiplicateStrings(numbers[0], numbers[1]);
        } else {
            return 0;
        }
    }

    private long multiplicateStrings(String s1, String s2) {
        try {
            int num1 = Integer.parseInt(s1);
            int num2 = Integer.parseInt(s2);
            if (num1 <= 999 && num2 <= 999) {
                return num1 * num2;
            } else {
                return 0;
            }
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

}
