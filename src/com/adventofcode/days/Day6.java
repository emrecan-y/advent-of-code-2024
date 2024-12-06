package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        Coordinates guardPos = findGuardPosition(map);
        System.out.println(getGuardDistinctPosCount(map, guardPos));
    }

    private int getGuardDistinctPosCount(List<List<Character>> map, Coordinates guardPos) {
        HashSet<Coordinates> distinctPositions = new HashSet<>();
        Character guard = map.get(guardPos.getY()).get(guardPos.getX());
        while (true) {
            distinctPositions.add(new Coordinates(guardPos.getX(), guardPos.getY()));
            Coordinates move = getMove(guard);
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

        // for (int y = 0; y < map.size(); y++) {
        // for (int x = 0; x < map.get(y).size(); x++) {
        // if (distinctPositions.contains(new Coordinates(x, y))) {
        // System.out.print('X');
        // } else {
        // System.out.print(map.get(y).get(x));
        // }
        // }
        // System.out.println();
        // }
        return distinctPositions.size();
    }

    private Coordinates findGuardPosition(List<List<Character>> map) {
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) == GUARD_STARTING_DIR) {
                    return new Coordinates(x, y);
                }
            }
        }
        return null;
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

    private Coordinates getMove(char guard) {
        switch (guard) {
            case '^':
                return new Coordinates(0, -1);
            case '>':
                return new Coordinates(1, 0);
            case 'v':
                return new Coordinates(0, 1);
            case '<':
                return new Coordinates(-1, 0);
            default:
                return null;
        }
    }

    @Override
    public void partTwo() {

    }

    private class Coordinates {
        private int x;
        private int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Coordinates other = (Coordinates) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        private Day6 getEnclosingInstance() {
            return Day6.this;
        }

    }

}
