package com.adventofcode.days;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day14 extends AbstractAdventDay {

    private final static int BATHROOM_X = 101; // 101;
    private final static int BATHROOM_Y = 103;// 103;
    private final static int SECONDS = 100;

    public Day14() {
        super(14);
    }

    public static void main(String[] args) {
        Day14 day = new Day14();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        List<Robot> robots = getRobots();
        IntStream.range(0, SECONDS).forEach((i) -> robots.stream().forEach(robot -> robot.moveRobot()));

        Map<Coordinate, Long> robotCount = robots.stream()
                .collect(Collectors.groupingBy(robot -> robot.position, Collectors.counting()));

        Coordinate[] topLeft = {
                new Coordinate(0, 0),
                new Coordinate(BATHROOM_X / 2 + 1, 0),
                new Coordinate(0, BATHROOM_Y / 2 + 1),
                new Coordinate(BATHROOM_X / 2 + 1, BATHROOM_Y / 2 + 1)
        };

        Coordinate[] bottomRight = {
                new Coordinate(BATHROOM_X / 2, BATHROOM_Y / 2),
                new Coordinate(BATHROOM_X, BATHROOM_Y / 2),
                new Coordinate(BATHROOM_X / 2, BATHROOM_Y),
                new Coordinate(BATHROOM_X, BATHROOM_Y)
        };

        long result = IntStream.range(0, topLeft.length)
                .mapToLong(i -> getRobotCountInArea(robotCount, topLeft[i], bottomRight[i]))
                .reduce(1L, (a, b) -> a * b);

        System.out.println(result);

    }

    @Override
    public void partTwo() {

    }

    private long getRobotCountInArea(Map<Coordinate, Long> robotCount, Coordinate start, Coordinate end) {
        long robotCountInArea = 0;
        for (int y = start.getY(); y < end.getY(); y++) {
            for (int x = start.getX(); x < end.getX(); x++) {
                robotCountInArea += robotCount.getOrDefault(new Coordinate(x, y), 0L);
            }
        }
        return robotCountInArea;
    }

    private void printRobots(List<Robot> robots) {
        List<Coordinate> robotPositions = robots.stream().map(robot -> robot.position).toList();
        for (int y = 0; y < BATHROOM_Y; y++) {
            for (int x = 0; x < BATHROOM_X; x++) {
                if (robotPositions.contains(new Coordinate(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    private List<Robot> getRobots() {
        return super.getInput().stream().map(s -> parseRobotLine(s)).collect(Collectors.toList());
    }

    private Robot parseRobotLine(String s) {
        String position = s.substring(s.indexOf("=") + 1, s.indexOf(" "));
        String velocity = s.substring(s.lastIndexOf("=") + 1);
        return new Robot(parseCoordinate(position), parseCoordinate(velocity));
    }

    private Coordinate parseCoordinate(String s) {
        String[] coordinates = s.split(",");
        return new Coordinate(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
    }

    private class Robot {
        Coordinate position;
        Coordinate velocity;

        private Robot(Coordinate position, Coordinate velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        private void moveRobot() {
            Coordinate newPosition = Coordinate.add(position, velocity);

            // compansate for moves outside of the bathroom size
            newPosition.setX((newPosition.getX() + BATHROOM_X) % BATHROOM_X);
            newPosition.setY((newPosition.getY() + BATHROOM_Y) % BATHROOM_Y);

            this.position = newPosition;
        }
    }

}
