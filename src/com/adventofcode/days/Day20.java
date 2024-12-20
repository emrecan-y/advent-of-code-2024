package com.adventofcode.days;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day20 extends AbstractAdventDay {

    private static final char WALL = '#';
    private static final char TRACK = '.';

    private static final char START = 'S';
    private static final char END = 'E';

    public Day20() {
        super(20);
    }

    public static void main(String[] args) {
        Day20 day = new Day20();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        // copied the solution from day18 as a start
        // then did a brute force search with every single wall removed, so execution
        // time could be improved, but for now its enough to keep the streak on
        List<List<Character>> map2d = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());

        Coordinate startCoordinate = findCharInMap(map2d, START);
        Coordinate endCoordinate = findCharInMap(map2d, END);

        Map<Integer, Integer> timeSavingMap = getTimeSavingMap(map2d, startCoordinate, endCoordinate);
        Long result = timeSavingMap.entrySet().stream().filter(e -> e.getKey() >= 100).mapToLong(e -> e.getValue())
                .sum();
        System.out.println(result);
    }

    @Override
    public void partTwo() {

    }

    private Map<Integer, Integer> getTimeSavingMap(List<List<Character>> map2d, Coordinate startCoordinate,
            Coordinate endCoordinate) {

        int baseLineStepCount = getMinimunStepCount(map2d, startCoordinate, endCoordinate);
        Map<Integer, Integer> timeSavingMap = new HashMap<>();

        // brute force search with every single wall removed
        for (int y = 1; y < map2d.size() - 1; y++) {
            for (int x = 1; x < map2d.get(y).size() - 1; x++) {
                List<Character> yList = map2d.get(y);
                if (yList.get(x) == WALL) {
                    yList.set(x, TRACK);
                    int currentStepCount = getMinimunStepCount(map2d, startCoordinate, endCoordinate);
                    if (currentStepCount < baseLineStepCount) {
                        timeSavingMap.merge(baseLineStepCount - currentStepCount, 1, Integer::sum);
                    }
                    yList.set(x, WALL);
                }
            }
        }
        return timeSavingMap;
    }

    private Coordinate findCharInMap(List<List<Character>> map, char charToFind) {
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) == charToFind) {
                    return new Coordinate(x, y);
                }
            }
        }
        return null;
    }

    private int getMinimunStepCount(List<List<Character>> map2d, Coordinate startCoordinate, Coordinate endCoordinate) {
        Coordinate[] directions = { new Coordinate(0, 1), new Coordinate(0, -1), new Coordinate(1, 0),
                new Coordinate(-1, 0) };

        LinkedList<Step> bfs = new LinkedList<>();
        bfs.add(new Step(startCoordinate, 0));
        Set<Coordinate> visited = new HashSet<>();

        while (!visited.contains(endCoordinate) && !bfs.isEmpty()) {
            Step step = bfs.poll();
            for (Coordinate direction : directions) {
                exploreNeighbor(step, direction, bfs, visited, map2d);
            }
        }

        return bfs.isEmpty() ? -1 : bfs.getLast().stepCount;
    }

    private void exploreNeighbor(Step step, Coordinate direction, LinkedList<Step> bfs,
            Set<Coordinate> visited, List<List<Character>> map2d) {
        Coordinate neighbour = Coordinate.add(step.coordinate, direction);
        int y = neighbour.getY();
        int x = neighbour.getX();
        boolean isInBounds = x >= 0 && y >= 0 && x < map2d.get(0).size() && y < map2d.size();
        if (isInBounds && !visited.contains(neighbour) && map2d.get(y).get(x) != WALL) {
            bfs.add(new Step(neighbour, step.stepCount + 1));
            visited.add(neighbour);
        }
    }

    private class Step {
        private Coordinate coordinate;
        private int stepCount;

        private Step(Coordinate coordinate, int stepCount) {
            this.coordinate = coordinate;
            this.stepCount = stepCount;
        }
    }

}
