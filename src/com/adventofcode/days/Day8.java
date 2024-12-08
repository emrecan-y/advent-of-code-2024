package com.adventofcode.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day8 extends AbstractAdventDay {

    public Day8() {
        super(8);
    }

    public static void main(String[] args) {
        Day8 day = new Day8();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        Map<Character, List<Coordinate>> frequencyMap = getFrequencyMap();
        final int xSize = super.getInput().get(0).length();
        final int ySize = super.getInput().size();

        Set<Coordinate> distinctAntinodeLocations = frequencyMap.values().stream()
                .flatMap(list -> getPairCombinationSetFromList(list).stream())
                .flatMap(pair -> getAntinodeCoordinates(pair).stream())
                .filter(c -> isCoordinateInsideMap(c, xSize, ySize))
                .collect(Collectors.toSet());

        System.out.println(distinctAntinodeLocations.size());
    }

    @Override
    public void partTwo() {
        Map<Character, List<Coordinate>> frequencyMap = getFrequencyMap();
        final int xSize = super.getInput().get(0).length();
        final int ySize = super.getInput().size();

        Set<Coordinate> distinctAntinodeLocations = frequencyMap.values().stream()
                .flatMap(list -> getPairCombinationSetFromList(list).stream())
                .flatMap(pair -> getExtendedAntinodeCoordinates(pair, xSize, ySize).stream())
                .collect(Collectors.toSet());

        System.out.println(distinctAntinodeLocations.size());
    }

    private Map<Character, List<Coordinate>> getFrequencyMap() {
        Map<Character, List<Coordinate>> frequencyMap = new HashMap<>();
        List<String> input = super.getInput();
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                Character currChar = input.get(y).charAt(x);
                if (currChar != '.') {
                    frequencyMap.merge(currChar, new ArrayList<>(List.of(new Coordinate(x, y))), (list, newVal) -> {
                        list.addAll(newVal);
                        return list;
                    });
                }
            }
        }
        return frequencyMap;
    }

    private List<Coordinate> getAntinodeCoordinates(List<Coordinate> coordinatePair) {
        Coordinate antenna1 = coordinatePair.get(0);
        Coordinate antenna2 = coordinatePair.get(1);

        int xDiff = antenna1.getX() - antenna2.getX();
        int yDiff = antenna1.getY() - antenna2.getY();

        Coordinate antinode1 = new Coordinate(antenna1.getX() + xDiff, antenna1.getY() + yDiff);
        Coordinate antinode2 = new Coordinate(antenna2.getX() - xDiff, antenna2.getY() - yDiff);

        return List.of(antinode1, antinode2);
    }

    private List<Coordinate> getExtendedAntinodeCoordinates(List<Coordinate> coordinatePair, int xSize, int ySize) {
        Coordinate antenna1 = coordinatePair.get(0);
        Coordinate antenna2 = coordinatePair.get(1);

        int xDiff = antenna1.getX() - antenna2.getX();
        int yDiff = antenna1.getY() - antenna2.getY();

        List<Coordinate> list = new ArrayList<>(List.of(antenna1, antenna2));

        Coordinate antinode1 = new Coordinate(antenna1.getX() + xDiff, antenna1.getY() + yDiff);
        while (isCoordinateInsideMap(antinode1, xSize, ySize)) {
            list.add(antinode1);
            antinode1 = new Coordinate(antinode1.getX() + xDiff, antinode1.getY() + yDiff);
        }

        Coordinate antinode2 = new Coordinate(antenna2.getX() - xDiff, antenna2.getY() - yDiff);
        while (isCoordinateInsideMap(antinode2, xSize, ySize)) {
            list.add(antinode2);
            antinode2 = new Coordinate(antinode2.getX() - xDiff, antinode2.getY() - yDiff);
        }

        return list;

    }

    private boolean isCoordinateInsideMap(Coordinate c, int xSize, int ySize) {
        if (c.getX() >= 0 && c.getX() < xSize && c.getY() >= 0 && c.getY() < ySize) {
            return true;
        } else {
            return false;
        }
    }

    // return all possible pair combinations from a list
    private <T> Set<List<T>> getPairCombinationSetFromList(List<T> list) {
        Set<List<T>> permutationPairSet = new HashSet<>();
        for (int firstIndex = 0; firstIndex < list.size(); firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < list.size(); secondIndex++) {
                if (firstIndex != secondIndex) {
                    List<T> newPair = List.of(list.get(firstIndex), list.get(secondIndex));
                    permutationPairSet.add(newPair);
                }
            }
        }
        return permutationPairSet;
    }

    private void printResult(List<List<Character>> map, Set<Coordinate> distinctAntinodeLocations) {
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(0).size(); x++) {
                if (distinctAntinodeLocations.contains(new Coordinate(x, y))) {
                    System.out.print('#');
                } else {
                    System.out.print(map.get(y).get(x));
                }
            }
            System.out.println();
        }
    }

}
