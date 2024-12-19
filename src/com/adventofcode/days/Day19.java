package com.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.adventofcode.AbstractAdventDay;

public class Day19 extends AbstractAdventDay {

    private Set<String> towels;
    private List<String> designs;

    public Day19() {
        super(19);
    }

    public static void main(String[] args) {
        Day19 day = new Day19();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        init();
        long possibleCounts = designs.stream().map(design -> designIsPossible(design, towels)).filter(e -> e).count();
        System.out.println(possibleCounts);
    }

    @Override
    public void partTwo() {
        init();

        Map<String, Long> cache = new HashMap<>();
        long possibleCounts = designs.stream()
                .mapToLong(design -> countDifferentWayOfPossibilites(design, towels, cache))
                .sum();

        System.out.println(possibleCounts);
    }

    private void init() {
        List<String> input = super.getInput();
        this.towels = new HashSet<>(Arrays.asList(input.get(0).split(", ")));
        this.designs = IntStream
                .range(2, input.size())
                .mapToObj(i -> input.get(i))
                .collect(Collectors.toList());
    }

    private boolean designIsPossible(String design, Set<String> towels) {
        for (int i = 1; i <= design.length(); i++) {
            String start = design.substring(0, i);
            String end = design.substring(i);
            if (towels.contains(start)) {
                if (end.equals("")) {
                    return true;
                } else if (designIsPossible(end, towels)) {
                    return true;
                }
            }
        }
        return false;
    }

    private long countDifferentWayOfPossibilites(String design, Set<String> towels, Map<String, Long> cache) {
        if (cache.containsKey(design)) {
            return cache.get(design);
        }
        long count = 0;
        for (int i = 1; i <= design.length(); i++) {
            String start = design.substring(0, i);
            String end = design.substring(i);
            if (towels.contains(start)) {
                if (end.equals("")) {
                    count++;
                } else {
                    count += countDifferentWayOfPossibilites(end, towels, cache);
                }
            }
        }
        cache.put(design, count);
        return count;
    }

}
