package com.adventofcode.days;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day10 extends AbstractAdventDay {

    public Day10() {
        super(10);
    }

    public static void main(String[] args) {
        Day10 day = new Day10();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        List<List<Character>> map2d = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());

        int sum = 0;
        for (int y = 0; y < map2d.size(); y++) {
            for (int x = 0; x < map2d.get(y).size(); x++) {
                if (map2d.get(y).get(x) == '0') {
                    sum += getTrailScroe(map2d, x, y, false);
                }
            }
        }

        System.out.println("The trailhead score is " + sum);
    }

    @Override
    public void partTwo() {
        List<List<Character>> map2d = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());

        int sum = 0;
        for (int y = 0; y < map2d.size(); y++) {
            for (int x = 0; x < map2d.get(y).size(); x++) {
                if (map2d.get(y).get(x) == '0') {
                    sum += getTrailScroe(map2d, x, y, true);
                }
            }
        }

        System.out.println("The trailhead rating is " + sum);
    }

    public int getTrailScroe(List<List<Character>> map2d, int x, int y, boolean getRating) {
        int traiHeadRating = 0;
        Set<Coordinate> distinctTrailEnds = new HashSet<>();

        Queue<Coordinate> bfs = new LinkedList<>();
        bfs.add(new Coordinate(x, y));

        while (!bfs.isEmpty()) {
            Coordinate coordinate = bfs.poll();
            x = coordinate.getX();
            y = coordinate.getY();
            Character character = map2d.get(coordinate.getY()).get(coordinate.getX());

            if (character == '9') {
                distinctTrailEnds.add(coordinate);
                traiHeadRating++;
                continue;
            }

            // check neighbouring tiles for a char greater by 1
            if (y < map2d.size() - 1 && character + 1 == map2d.get(y + 1).get(x)) {
                bfs.add(new Coordinate(x, y + 1));
            }
            if (y > 0 && character + 1 == map2d.get(y - 1).get(x)) {
                bfs.add(new Coordinate(x, y - 1));
            }
            if (x < map2d.get(0).size() - 1 && character + 1 == map2d.get(y).get(x + 1)) {
                bfs.add(new Coordinate(x + 1, y));
            }
            if (x > 0 && character + 1 == map2d.get(y).get(x - 1)) {
                bfs.add(new Coordinate(x - 1, y));
            }
        }

        if (getRating) {
            return traiHeadRating;
        } else {
            return distinctTrailEnds.size();
        }
    }

}
