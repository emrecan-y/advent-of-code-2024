package com.adventofcode.days;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.adventofcode.AbstractAdventDay;

public class Day7 extends AbstractAdventDay {

    public Day7() {
        super(7);
    }

    public static void main(String[] args) {
        Day7 day = new Day7();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        long calibrationCount = super.getInput().stream().mapToLong(s -> getCalibrationCount(s, true)).sum();
        System.out.println("The calibration count is " + calibrationCount);
    }

    @Override
    public void partTwo() {
        long calibrationCount = super.getInput().stream().mapToLong(s -> getCalibrationCount(s, false)).sum();
        System.out.println("The calibration count is " + calibrationCount);
    }

    private long getCalibrationCount(String s, boolean solvePart1) {
        long calibrationCount = 0;

        String[] equation = s.split(": ");
        long expectedResult = Long.parseLong(equation[0]);
        int[] numbers = Stream.of(equation[1].split(" ")).mapToInt(Integer::parseInt).toArray();

        List<String> allCombinations = gettAllCombinationsAsString(numbers.length - 1, solvePart1);
        for (String combination : allCombinations) {
            long actualResult = numbers[0];
            for (int i = 0; i < combination.length(); i++) {
                actualResult = calculateNumber(actualResult, numbers[i + 1], combination.charAt(i));
            }
            if (actualResult == expectedResult) {
                calibrationCount += actualResult;
                break;
            }
        }
        return calibrationCount;
    }

    private List<String> gettAllCombinationsAsString(int desiredLength, boolean solvePart1) {
        // get the maximum decimal value of possible combinations
        long possibleCombinations = solvePart1 ? (long) Math.pow(2, desiredLength) : (long) Math.pow(3, desiredLength);

        return LongStream.range(0, possibleCombinations)
                .mapToObj(decimalValue -> getCombinationString(decimalValue, desiredLength, solvePart1))
                .collect(Collectors.toList());
    }

    private String getCombinationString(long decimalValue, int desiredLength, boolean solvePart1) {
        // get binary/ternery representation of current decimal and add zeros if missing
        String combinationString = solvePart1 ? Long.toBinaryString(decimalValue) : toTernaryString(decimalValue);
        if (combinationString.length() < desiredLength) {
            String missingZeros = "0".repeat(desiredLength - combinationString.length());
            combinationString = missingZeros + combinationString;
        }
        return combinationString;
    }

    private long calculateNumber(long result, long nextNumber, char operation) {
        // 0 means addition, 1 means multiplication, 2 means join as string
        switch (operation) {
            case '0':
                return result + nextNumber;
            case '1':
                return result * nextNumber;
            case '2':
                String newNum = String.valueOf(result) + String.valueOf(nextNumber);
                return Long.parseLong(newNum);
            default:
                return 0;
        }
    }

    // Ternary (Base-3) -> 0, 1, 2
    public static String toTernaryString(long num) {
        if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (num != 0) {
            sb.append(num % 3);
            num /= 3;
        }
        return sb.reverse().toString();
    }

}
