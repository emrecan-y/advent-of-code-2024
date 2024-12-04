package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.AbstractAdventDay;

public class Day4 extends AbstractAdventDay {

    public Day4() {
        super(4);
    }

    public static void main(String[] args) {
        Day4 day = new Day4();
        day.partOne();
        day.partTwo();
    }

    @Override
    public void partOne() {
        List<List<Character>> input2d = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());
        int result = countXmasIn2dList(input2d);
        System.out.format("'XMAS' appears %d times\n", result);
    }

    @Override
    public void partTwo() {
        List<List<Character>> input2d = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());
        int result = getMasCount(input2d);
        System.out.format("'X-MAS' appears %d times\n", result);
    }

    private int countXmasIn2dList(List<List<Character>> input2d) {
        int count = 0;
        // horizontal count
        count += input2d.stream().mapToInt(l -> countXmasInList(l)).sum();
        // vertical count
        count += transposeList(input2d).stream().mapToInt(l -> countXmasInList(l)).sum();
        // diagonal count
        count += getDiagonalsList(input2d).stream().mapToInt(l -> countXmasInList(l)).sum();
        count += getDiagonalsList(flipList(input2d)).stream().mapToInt(l -> countXmasInList(l)).sum();
        return count;

    }

    private int countXmasInList(List<Character> list) {
        int count = 0;
        for (int i = 0; i < list.size() - 3; i++) {
            StringBuilder toCheck = new StringBuilder();
            for (int k = 0; k < 4; k++) {
                toCheck.append(list.get(i + k));
            }
            String toCheckString = toCheck.toString();
            if (toCheckString.equals("XMAS") || toCheckString.equals("SAMX"))
                count++;
        }
        return count;
    }

    private <T> List<List<T>> getDiagonalsList(List<List<T>> list2d) {
        List<List<T>> diagonalsList = new ArrayList<>();
        int rows = list2d.size();
        int cols = list2d.get(0).size();
        // Get diagonals starting from the first row
        for (int col = 0; col < cols; col++) {
            List<T> diagonal = new ArrayList<>();
            for (int i = 0, j = col; i < rows && j < cols; i++, j++) {
                diagonal.add(list2d.get(i).get(j));
            }
            diagonalsList.add(diagonal);
        }
        // Get diagonals starting from the first column - top row
        for (int row = 1; row < rows; row++) {
            List<T> diagonals = new ArrayList<>();
            for (int i = row, j = 0; i < rows && j < cols; i++, j++) {
                diagonals.add(list2d.get(i).get(j));
            }
            diagonalsList.add(diagonals);
        }
        return diagonalsList;
    }

    private <T> List<List<T>> transposeList(List<List<T>> list2d) {
        List<List<T>> transposed = new ArrayList<>();
        for (int x = 0; x < list2d.get(0).size(); x++) {
            List<T> list = new ArrayList<T>();
            transposed.add(list);
            for (int y = 0; y < list2d.size(); y++) {
                list.add(list2d.get(y).get(x));
            }
        }
        return transposed;
    }

    private <T> List<List<T>> flipList(List<List<T>> list2d) {
        List<List<T>> flipped = new ArrayList<>(list2d);
        flipped.stream().forEach(list -> Collections.reverse(list));
        return flipped;
    }

    private int getMasCount(List<List<Character>> input2d) {
        int count = 0;
        for (int y = 1; y < input2d.size() - 1; y++) {
            for (int x = 1; x < input2d.get(y).size() - 1; x++) {
                // |1 2| |M S| |M M| |S M| |S S|
                // | 3 | | A | | A | | A | | A |
                // |4 5| |M S| |S S| |S M| |M M|
                List<Character> mas = new ArrayList<>();
                mas.add(input2d.get(y - 1).get(x - 1));
                mas.add(input2d.get(y - 1).get(x + 1));
                mas.add(input2d.get(y).get(x));
                mas.add(input2d.get(y + 1).get(x - 1));
                mas.add(input2d.get(y + 1).get(x + 1));
                if (isValidMas(mas)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValidMas(List<Character> mas) {
        Set<String> validMases = new HashSet<>();
        validMases.add("MSAMS");
        validMases.add("MMASS");
        validMases.add("SMASM");
        validMases.add("SSAMM");
        return validMases.contains(mas.stream().map(c -> c.toString()).collect(Collectors.joining()));
    }

}
