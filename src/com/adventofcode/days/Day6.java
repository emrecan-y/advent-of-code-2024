package com.adventofcode.days;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import com.adventofcode.misc.Coordinate;

import com.adventofcode.AbstractAdventDay;

public class Day6 extends AbstractAdventDay {
    private static final char OBSTRUCTION = '#';
    private static final char GUARD_STARTING_DIR = '^';

    public Day6() {
        super(6);
    }

    public static void main(String[] args) {
        Day6 day = new Day6();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        List<List<Character>> map = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());

        Coordinate guardPos = findGuardPosition(map);
        System.out.println(getGuardDistinctPosCount(map, guardPos));
    }

    @Override
    public void partTwo() {
        List<List<Character>> map = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());

        Coordinate guardPos = findGuardPosition(map);
        System.out.println(getCycleCount(map, guardPos));
    }

    private Coordinate findGuardPosition(List<List<Character>> map) {
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) == GUARD_STARTING_DIR) {
                    return new Coordinate(x, y);
                }
            }
        }
        return null;
    }

    private int getGuardDistinctPosCount(List<List<Character>> map, Coordinate guardPos) {
        HashSet<Coordinate> distinctPositions = new HashSet<>();
        Character guard = map.get(guardPos.getY()).get(guardPos.getX());

        int countSetSizeUnchanged = 0;
        while (true) {
            Coordinate guardPosCopy = new Coordinate(guardPos.getX(), guardPos.getY());
            if (distinctPositions.add(guardPosCopy)) {
                countSetSizeUnchanged = 0;
            } else if (countSetSizeUnchanged > 1000) {
                return -1;
            } else {
                countSetSizeUnchanged++;
            }

            Coordinate move = getMove(guard);
            int newY = guardPos.getY() + move.getY();
            int newX = guardPos.getX() + move.getX();
            if (newX < 0 || newX >= map.get(0).size() ||
                    newY < 0 || newY >= map.size()) {
                // guard is free
                break;
            } else if (map.get(newY).get(newX) == OBSTRUCTION) {
                guard = rotateGuard(guard);
            } else {
                guardPos.setX(newX);
                guardPos.setY(newY);
            }
        }
        return distinctPositions.size();
    }

    private int getCycleCount(List<List<Character>> map, Coordinate guardPos) {
        int count = 0;
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (x == guardPos.getX() && y == guardPos.getY()) {
                    continue;
                }
                if (map.get(y).get(x) != OBSTRUCTION) {
                    map.get(y).set(x, OBSTRUCTION);
                    Coordinate guardPosCopy = new Coordinate(guardPos.getX(), guardPos.getY());
                    if (getGuardDistinctPosCount(map, guardPosCopy) == -1) {
                        count++;
                    }
                    map.get(y).set(x, '.');
                }
            }
        }
        return count;
    }

    private char rotateGuard(char guard) {
        switch (guard) {
            case '^':
                return '>';
            case '>':
                return 'v';
            case 'v':
                return '<';
            case '<':
                return '^';
            default:
                return ' ';
        }
    }

    private Coordinate getMove(char guard) {
        switch (guard) {
            case '^':
                return new Coordinate(0, -1);
            case '>':
                return new Coordinate(1, 0);
            case 'v':
                return new Coordinate(0, 1);
            case '<':
                return new Coordinate(-1, 0);
            default:
                return null;
        }
    }

}
