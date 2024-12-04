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
        super(0);
    }

    public static void main(String[] args) {
        Day4 day = new Day4();
        // day.partOne();
        day.partTwo();
    }

    @Override
    public void partOne() {
        List<List<Character>> input2d = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());
        int result = countXmasIn2dList(input2d);
        System.out.println(result);

        // 2490 - 2531
    }

    private int countXmasIn2dList(List<List<Character>> input2d) {
        int count = 0;
        List<List<Character>> input2dTransposed = transposeList(input2d);
        // horizontal count
        count += input2d.stream().mapToInt(l -> countXmasInList(l)).sum();
        // vertical count
        count += input2dTransposed.stream().mapToInt(l -> countXmasInList(l)).sum();
        // diagonal count
        count += getDiagonalList(input2d).stream().mapToInt(l -> countXmasInList(l)).sum();
        printList(getDiagonalList(input2d));
        flipList(input2d);
        count += getDiagonalList(input2d).stream().mapToInt(l -> countXmasInList(l)).sum();
        return count;

    }

    private int countXmasInList(List<Character> list) {
        int count = 0;
        for (int i = 0; i < list.size() - 3; i++) {
            if (list.get(i) == 'X' && list.get(i + 1) == 'M' && list.get(i + 2) == 'A' && list.get(i + 3) == 'S' ||
                    list.get(i) == 'S' && list.get(i + 1) == 'A' && list.get(i + 2) == 'M' && list.get(i + 3) == 'X') {
                count++;
            }
        }
        return count;
    }

    private <T> List<List<T>> getDiagonalList(List<List<T>> list2d) {
        List<List<T>> diags = new ArrayList<>();
        int rows = list2d.size();
        int cols = list2d.get(0).size();
        // Get diagonals starting from the first row
        for (int col = 0; col < cols; col++) {
            List<T> diag = new ArrayList<>();
            for (int i = 0, j = col; i < rows && j < cols; i++, j++) {
                diag.add(list2d.get(i).get(j));
            }
            diags.add(diag);
        }
        // Get diagonals starting from the first column - top row
        for (int row = 1; row < rows; row++) {
            List<T> diag = new ArrayList<>();
            for (int i = row, j = 0; i < rows && j < cols; i++, j++) {
                diag.add(list2d.get(i).get(j));
            }
            diags.add(diag);
        }

        return diags;
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

    private <T> void flipList(List<List<T>> list2d) {
        list2d.stream().forEach(list -> Collections.reverse(list));
    }

    private <T> void printList(List<List<T>> list2d) {
        for (List<T> list : list2d) {
            for (T listItem : list) {
                if (listItem != null) {
                    System.out.print(listItem);
                } else {
                    System.out.print(" ");

                }
            }
            System.out.println();
        }
    }

    @Override
    public void partTwo() {
        List<List<Character>> input2d = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());
        int result = getMasCount(input2d);
        System.out.println(result);
    }

    private int getMasCount(List<List<Character>> input2d) {
        int count = 0;
        for (int y = 1; y < input2d.size() - 1; y++) {
            for (int x = 1; x < input2d.get(y).size() - 1; x++) {
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
        // topLeft, topRight, middle, bottomLeft, bottomRight
        Set<String> validMases = new HashSet<>();
        validMases.add("MSAMS");
        validMases.add("MMASS");
        validMases.add("SMASM");
        validMases.add("SSAMM");
        return validMases.contains(mas.stream().map(c -> c.toString()).collect(Collectors.joining()));
    }

}
