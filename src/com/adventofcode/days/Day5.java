package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.AbstractAdventDay;

public class Day5 extends AbstractAdventDay {

    public Day5() {
        super(5);
    }

    public static void main(String[] args) {
        Day5 day = new Day5();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        Map<String, Set<String>> orderingMap = new HashMap<>();
        List<List<String>> pageNumbersList = new ArrayList<>();
        parseInput(orderingMap, pageNumbersList);

        int sum = 0;
        for (List<String> pageNumbers : pageNumbersList) {
            if (isValidPage(pageNumbers, orderingMap)) {
                sum += Integer.parseInt(pageNumbers.get(pageNumbers.size() / 2));
            }
        }
        System.out.println("The sum of middle page numbers from the correctly-ordered lists is " + sum);
    }

    @Override
    public void partTwo() {
        Map<String, Set<String>> orderingMap = new HashMap<>();
        List<List<String>> pageNumbersList = new ArrayList<>();
        parseInput(orderingMap, pageNumbersList);

        int sum = 0;
        for (List<String> pageNumbers : pageNumbersList) {
            if (!isValidPage(pageNumbers, orderingMap)) {
                List<String> orderedNumbers = orderPage(pageNumbers, orderingMap);
                sum += Integer.parseInt(orderedNumbers.get(orderedNumbers.size() / 2));
            }
        }
        System.out.println("The sum of middle page numbers from the not correctly-ordered lists is " + sum);
    }

    private boolean isValidPage(List<String> pageNumbers, Map<String, Set<String>> orderingMap) {
        // check from back
        for (int i = pageNumbers.size() - 1; i >= 0; i--) {
            Set<String> set = orderingMap.get(pageNumbers.get(i));
            if (set == null) {
                continue;
            }
            for (int k = i; k >= 0; k--) {
                if (set.contains(pageNumbers.get(k))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void parseInput(Map<String, Set<String>> orderingMap, List<List<String>> pageNumbersList) {
        boolean topPart = true;
        for (String s : super.getInput()) {
            if (s.equals("")) {
                topPart = false;
                continue;
            }
            if (topPart) {
                String[] numbers = s.split("\\|");
                orderingMap.merge(numbers[0], new HashSet<>(Set.of(numbers[1])), (set, val) -> {
                    set.addAll(val);
                    return set;
                });
            } else {
                List<String> pageNumbers = new ArrayList<>();
                Stream.of(s.split(",")).forEach(sub -> pageNumbers.add(sub));
                pageNumbersList.add(pageNumbers);
            }
        }
    }

    private List<String> orderPage(List<String> pageNumbers, Map<String, Set<String>> orderingMap) {
        // same algorithm from isValidPage(), it just swaps the two current indices in
        // the list when there is a wrong ordered pair
        List<String> ordered = new ArrayList<>(pageNumbers);
        for (int i = ordered.size() - 1; i >= 0; i--) {
            Set<String> set = orderingMap.get(ordered.get(i));
            if (set == null) {
                continue;
            }
            for (int k = i; k >= 0; k--) {
                if (set.contains(ordered.get(k))) {
                    Collections.swap(ordered, k, i);
                    // reset outer loop
                    i = ordered.size();
                    break;
                }
            }
        }
        return ordered;
    }

}
