package com.adventofcode.days;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.adventofcode.AbstractAdventDay;

public class Day2 extends AbstractAdventDay {

    public Day2() {
        super(2);
    }

    public static void main(String[] args) {
        Day2 day = new Day2();
        day.partOne();
        day.partTwo();
    }

    @Override
    public void partOne() {
        long safeReportCount = super.getInput().stream()
                .filter(s -> isSafeReport(Stream.of(s.split(" ")).map(Integer::parseInt).toList()))
                .count();
        System.out.println("The count of safe reports is " + safeReportCount);
    }

    @Override
    public void partTwo() {
        long safeReportCount = super.getInput().stream()
                .filter(s -> isSafeReportWithDampener(
                        Stream.of(s.split(" ")).map(Integer::parseInt).collect(Collectors.toList())))
                .count();
        System.out.println("The dampened count of safe reports is " + safeReportCount);
    }

    private boolean isSafeReport(List<Integer> levels) {
        boolean isAscending = levels.get(0) < levels.get(1);
        int min = isAscending ? -3 : 1;
        int max = isAscending ? -1 : 3;
        for (int i = 0; i < levels.size() - 1; i++) {
            int difference = levels.get(i) - levels.get(i + 1);
            if (difference < min || difference > max) {
                return false;
            }
        }
        return true;
    }

    private boolean isSafeReportWithDampener(List<Integer> levels) {
        if (isSafeReport(levels)) {
            return true;
        } else {
            // Brute Force
            for (int i = 0; i < levels.size(); i++) {
                Integer storeForDeletion = levels.get(i);
                levels.remove(i);
                if (isSafeReport(levels)) {
                    return true;
                }
                levels.add(i, storeForDeletion);
            }
        }
        return false;
    }
}
