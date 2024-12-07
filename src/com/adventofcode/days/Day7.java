package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
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
        long calibrationCount = super.getInput().stream().mapToLong(s -> getCalibrationCount(s)).sum();
        System.out.println("The calibration count is " + calibrationCount);
    }

    @Override
    public void partTwo() {
        long calibrationCount = super.getInput().stream().mapToLong(s -> getCalibrationCountPipe(s)).sum();
        System.out.println("The calibration count is " + calibrationCount);
    }

    private long getCalibrationCount(String s) {
        long sumOfTestValues = 0;

        String[] equation = s.split(": ");
        long expectedResult = Long.parseLong(equation[0]);
        int[] numbers = Stream.of(equation[1].split(" ")).mapToInt(Integer::parseInt).toArray();

        List<String> allCombinations = gettAllCombinationsFromBinaryString(numbers.length - 1);

        for (String combination : allCombinations) {
            long actualResult = numbers[0];
            for (int i = 0; i < combination.length(); i++) {
                // 0 means addition, 1 means multiplication
                if (combination.charAt(i) == '0') {
                    actualResult += numbers[i + 1];
                } else {
                    actualResult *= numbers[i + 1];
                }
            }
            if (actualResult == expectedResult) {
                sumOfTestValues += actualResult;
                break;
            }
        }

        return sumOfTestValues;
    }

    private long getCalibrationCountPipe(String s) {
        long sumOfTestValues = 0;

        String[] equation = s.split(": ");
        long expectedResult = Long.parseLong(equation[0]);
        int[] numbers = Stream.of(equation[1].split(" ")).mapToInt(Integer::parseInt).toArray();

        List<String> allCombinations = gettAllCombinationsFromTernaryString(numbers.length - 1);

        for (String combination : allCombinations) {
            long actualResult = numbers[0];
            for (int i = 0; i < combination.length(); i++) {
                // 0 means addition, 1 means multiplication, 2 means pipe
                switch (combination.charAt(i)) {
                    case '0':
                        actualResult += numbers[i + 1];
                        break;
                    case '1':
                        actualResult *= numbers[i + 1];
                        break;
                    case '2':
                        String newNum = String.valueOf(actualResult) + String.valueOf(numbers[i + 1]);
                        actualResult = Long.parseLong(newNum);
                        break;
                    default:
                        break;
                }
            }
            if (actualResult == expectedResult) {
                sumOfTestValues += actualResult;
                break;
            }
        }

        return sumOfTestValues;
    }

    private List<String> gettAllCombinationsFromBinaryString(int desiredLength) {
        List<String> combinations = new ArrayList<>();
        long startingValue = (int) Math.pow(2, desiredLength) - 1;
        while (startingValue >= 0) {
            String binaryString = Long.toBinaryString(startingValue);
            if (binaryString.length() < desiredLength) {
                String missingZeros = "0".repeat(desiredLength - binaryString.length());
                binaryString = missingZeros + binaryString;
            }
            combinations.add(binaryString);
            startingValue--;
        }
        return combinations;
    }

    private List<String> gettAllCombinationsFromTernaryString(int desiredLength) {
        List<String> combinations = new ArrayList<>();
        long startingValue = (int) Math.pow(3, desiredLength) - 1;
        while (startingValue >= 0) {
            String ternaryString = toTernaryString(startingValue);
            if (ternaryString.length() < desiredLength) {
                String missingZeros = "0".repeat(desiredLength - ternaryString.length());
                ternaryString = missingZeros + ternaryString;
            }
            combinations.add(ternaryString);
            startingValue--;
        }
        return combinations;
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
