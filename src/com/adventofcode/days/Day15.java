package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day15 extends AbstractAdventDay {

    private final static char ROBOT = '@';
    private final static char BOX = 'O';
    private final static char WALL = '#';
    private final static char FREE_SPACE = '.';

    public Day15() {
        super(15);
    }

    public static void main(String[] args) {
        Day15 day = new Day15();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        List<List<Character>> map2d = parseMap();
        List<Character> moves = parseMoves();
        Coordinate robotPos = getRobotPos(map2d);

        for (Character move : moves) {
            Coordinate moveValue = getMoveValue(move);
            robotPos = moveRobot(map2d, robotPos, moveValue);
            // printMap(map2d);
        }

        System.out.println(getGpsSum(map2d));
    }

    private long getGpsSum(List<List<Character>> map2d) {
        long gpsSum = 0L;
        for (int y = 0; y < map2d.size(); y++) {
            for (int x = 0; x < map2d.get(y).size(); x++) {
                if (map2d.get(y).get(x) == BOX) {
                    gpsSum += y * 100 + x;
                }
            }
        }
        return gpsSum;
    }

    private Coordinate moveRobot(List<List<Character>> map2d, Coordinate robotPos, Coordinate moveValue) {
        if (moveIsPossible(map2d, robotPos, moveValue)) {
            Character robot = map2d.get(robotPos.getY()).get(robotPos.getX());
            Coordinate nextTile = Coordinate.add(robotPos, moveValue);
            while (robot != FREE_SPACE) {
                swapIn2dList(map2d, robotPos, nextTile);
                nextTile = Coordinate.add(nextTile, moveValue);
                robot = map2d.get(robotPos.getY()).get(robotPos.getX());
            }
            robotPos = Coordinate.add(robotPos, moveValue);
        }
        return robotPos;
    }

    private boolean moveIsPossible(List<List<Character>> map2d, Coordinate robotPos, Coordinate moveValue) {
        Coordinate nextTile = Coordinate.add(robotPos, moveValue);
        Character nextTileContent = map2d.get(nextTile.getY()).get(nextTile.getX());
        while (nextTileContent != WALL && nextTileContent != FREE_SPACE) {
            nextTile = Coordinate.add(nextTile, moveValue);
            nextTileContent = map2d.get(nextTile.getY()).get(nextTile.getX());
        }
        return nextTileContent == FREE_SPACE;
    }

    private Coordinate getMoveValue(char move) {
        switch (move) {
            case '^':
                return new Coordinate(0, -1);
            case '>':
                return new Coordinate(1, 0);
            case 'v':
                return new Coordinate(0, 1);
            case '<':
                return new Coordinate(-1, 0);
            default:
                return new Coordinate(0, 0);
        }
    }

    private Coordinate getRobotPos(List<List<Character>> map2d) {
        for (int y = 0; y < map2d.size(); y++) {
            for (int x = 0; x < map2d.get(y).size(); x++) {
                if (map2d.get(y).get(x) == ROBOT) {
                    return new Coordinate(x, y);
                }
            }
        }
        return null;
    }

    private void printMap(List<List<Character>> map2d) {
        for (int y = 0; y < map2d.size(); y++) {
            for (int x = 0; x < map2d.get(y).size(); x++) {
                System.out.print(map2d.get(y).get(x));
            }
            System.out.println();
        }
    }

    private List<List<Character>> parseMap() {
        List<List<Character>> map2d = new ArrayList<>();
        for (String s : super.getInput()) {
            if (s.equals("")) {
                break;
            }
            map2d.add(s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        }
        return map2d;
    }

    private List<Character> parseMoves() {
        List<Character> moves = new ArrayList<>();
        boolean movesFound = false;
        for (String s : super.getInput()) {
            if (s.equals("")) {
                movesFound = true;
                continue;
            }
            if (movesFound) {
                moves.addAll(s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
            }
        }
        return moves;
    }

    private <T> void swapIn2dList(List<List<T>> list2d, Coordinate c1, Coordinate c2) {
        T item1 = list2d.get(c1.getY()).get(c1.getX());
        T item2 = list2d.get(c2.getY()).get(c2.getX());

        list2d.get(c2.getY()).set(c2.getX(), item1);
        list2d.get(c1.getY()).set(c1.getX(), item2);

    }

    @Override
    public void partTwo() {
    }

}
