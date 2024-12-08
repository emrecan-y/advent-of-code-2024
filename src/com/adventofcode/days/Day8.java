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
        List<List<Character>> map = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());

        final int ySize = map.size();
        final int xSize = map.get(0).size();
        Map<Character, List<Coordinate>> frequencyMap = new HashMap<>();

        // create a map of the antennas with the same frequncy and their locations
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                Character currChar = map.get(y).get(x);
                if (currChar != '.') {
                    frequencyMap.merge(currChar, new ArrayList<>(List.of(new Coordinate(x, y))), (list, newVal) -> {
                        list.addAll(newVal);
                        return list;
                    });
                }
            }
        }

        Set<Coordinate> distinctAntinodeLocations = frequencyMap.values().stream()
                .flatMap(list -> getPermutationPairsOfList(list).stream())
                .flatMap(pair -> getAntinodeCoordinates(pair).stream())
                .filter(c -> isCoordinateInsideMap(c, xSize, ySize))
                .collect(Collectors.toSet());

        System.out.println(distinctAntinodeLocations.size());
    }

    @Override
    public void partTwo() {

    }

    private boolean isCoordinateInsideMap(Coordinate c, int xSize, int ySize) {
        if (c.getX() >= 0 && c.getX() < xSize && c.getY() >= 0 && c.getY() < ySize) {
            return true;
        } else {
            return false;
        }
    }

    private List<Coordinate> getAntinodeCoordinates(List<Coordinate> coordinatePair) {
        Coordinate antenna1 = coordinatePair.get(0);
        Coordinate antenna2 = coordinatePair.get(1);
        if (antenna1.equals(antenna2)) {
            return new ArrayList<>();
        }
        int xDiff = antenna1.getX() - antenna2.getX();
        int yDiff = antenna1.getY() - antenna2.getY();

        Coordinate antinode1 = new Coordinate(antenna1.getX() + xDiff, antenna1.getY() + yDiff);
        Coordinate antinode2 = new Coordinate(antenna2.getX() - xDiff, antenna2.getY() - yDiff);
        return List.of(antinode1, antinode2);

    }

    private <T> Set<List<T>> getPermutationPairsOfList(List<T> list) {
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

    private void printResult(List<List<Character>> map, HashSet<Coordinate> distinctAntinodeLocations) {
        // print map
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(0).size(); x++) {
                if (distinctAntinodeLocations.contains(new Coordinate(x, y))) {
                    System.out.print('#');
                } else {
                    System.out.print(map.get(y).get(x));
                }
            }
            System.err.println();
        }
    }

}
