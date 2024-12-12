package com.adventofcode.days;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day12 extends AbstractAdventDay {

    public Day12() {
        super(12);
    }

    public static void main(String[] args) {
        Day12 day = new Day12();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        Map<Character, Integer> edgeCount = new HashMap<>();

        List<List<Character>> map2d = super.getInput().stream()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());

        for (int y = 0; y < map2d.size(); y++) {
            for (int x = 0; x < map2d.get(y).size(); x++) {
                edgeCount.merge(map2d.get(y).get(x), getFencePrice(map2d, x, y), Integer::sum);
            }
        }
        int sum = edgeCount.keySet().stream().mapToInt(p -> edgeCount.get(p)).sum();
        System.out.println(sum);

    }

    @Override
    public void partTwo() {

    }

    private int getFencePrice(List<List<Character>> map2d, int x, int y) {
        Character plot = map2d.get(y).get(x);
        if (plot == '#') {
            return 0;
        }

        Queue<Coordinate> bfs = new LinkedList<>();
        Set<Coordinate> visitedPlots = new HashSet<>();
        bfs.add(new Coordinate(x, y));
        int edgeCount = 0;

        while (!bfs.isEmpty()) {
            Coordinate coordinate = bfs.poll();
            visitedPlots.add(coordinate);

            edgeCount += getEdgeCountForPlot(map2d, plot, coordinate, new Coordinate(-1, 0), visitedPlots, bfs);
            edgeCount += getEdgeCountForPlot(map2d, plot, coordinate, new Coordinate(1, 0), visitedPlots, bfs);
            edgeCount += getEdgeCountForPlot(map2d, plot, coordinate, new Coordinate(0, -1), visitedPlots, bfs);
            edgeCount += getEdgeCountForPlot(map2d, plot, coordinate, new Coordinate(0, 1), visitedPlots, bfs);
        }

        visitedPlots.forEach(c -> map2d.get(c.getY()).set(c.getX(), '#'));

        return edgeCount * visitedPlots.size();
    }

    private int getEdgeCountForPlot(List<List<Character>> map2d, Character plot, Coordinate coordinate,
            Coordinate directionOffset, Set<Coordinate> visitedPlots, Queue<Coordinate> bfs) {

        int edgeCount = 0;
        int newX = coordinate.getX() + directionOffset.getX();
        int newY = coordinate.getY() + directionOffset.getY();

        boolean isEdgePlot = newX < 0 || newX >= map2d.get(0).size() || newY < 0 || newY >= map2d.size();

        if (isEdgePlot || map2d.get(newY).get(newX) != plot) {
            edgeCount++;
        } else {
            Coordinate newCoordinate = new Coordinate(newX, newY);
            if (!visitedPlots.contains(newCoordinate)) {
                bfs.add(newCoordinate);
                visitedPlots.add(newCoordinate);
            }
        }

        return edgeCount;
    }

}