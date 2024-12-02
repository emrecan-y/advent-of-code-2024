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
                .map(s -> isSafeReport(Stream.of(s.split(" ")).map(Integer::parseInt).toList()))
                .filter(b -> b == true)
                .count();
        System.out.println("The count of safe reports is " + safeReportCount);
    }

    @Override
    public void partTwo() {
        long safeReportCount = super.getInput().stream()
                .map(s -> isSafeReportWithDampener(
                        Stream.of(s.split(" ")).map(Integer::parseInt).collect(Collectors.toList())))
                .filter(b -> b == true)
                .count();
        System.out.println("The count of safe reports is " + safeReportCount);
    }

    private boolean isSafeReport(List<Integer> levels) {
        boolean isAscending = levels.get(0) > levels.get(1) ? false : true;
        for (int i = 0; i < levels.size() - 1; i++) {
            if (!isAscending && (levels.get(i) - levels.get(i + 1) >= 1 && levels.get(i) - levels.get(i + 1) <= 3) ||
                    isAscending
                            && (levels.get(i) - levels.get(i + 1) >= -3 && levels.get(i) - levels.get(i + 1) <= -1)) {
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isSafeReportWithDampener(List<Integer> levels) {
        if (isSafeReport(levels)) {
            return true;
        } else {
            for (int i = 0; i < levels.size(); i++) {
                Integer buffer = levels.get(i);
                levels.remove(i);
                if (isSafeReport(levels)) {
                    return true;
                }
                levels.add(i, buffer);
            }
        }
        return false;
    }

}
