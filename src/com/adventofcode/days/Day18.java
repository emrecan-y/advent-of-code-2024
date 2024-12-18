package com.adventofcode.days;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day18 extends AbstractAdventDay {

    private static final int MEMORY_SIZE = 71;
    private static final int BYTE_LIMIT = 1024;
    private static final Coordinate START = new Coordinate(0, 0);
    private static final Coordinate END = new Coordinate(MEMORY_SIZE - 1, MEMORY_SIZE - 1);

    public Day18() {
        super(18);
    }

    public static void main(String[] args) {
        Day18 day = new Day18();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        List<String> input = super.getInput();
        boolean[][] memory = new boolean[MEMORY_SIZE][MEMORY_SIZE];
        for (int i = 0; i < BYTE_LIMIT && i < input.size(); i++) {
            int[] numbers = Stream.of(input.get(i).split(",")).mapToInt(Integer::parseInt).toArray();
            memory[numbers[1]][numbers[0]] = true;
        }
        System.out.println(getMinimunStepCount(memory));
    }

    @Override
    public void partTwo() {
        List<String> input = super.getInput();
        boolean[][] corruptedMemory = new boolean[MEMORY_SIZE][MEMORY_SIZE];

        for (int i = 0; i < input.size(); i++) {
            int[] numbers = Stream.of(input.get(i).split(",")).mapToInt(Integer::parseInt).toArray();
            corruptedMemory[numbers[1]][numbers[0]] = true;
            if (i < BYTE_LIMIT) {
                continue;
            }
            if (getMinimunStepCount(corruptedMemory) == -1) {
                System.out.println(input.get(i));
                break;
            }
        }

    }

    private int getMinimunStepCount(boolean[][] corruptedMemory) {
        Coordinate[] directions = { new Coordinate(0, 1), new Coordinate(0, -1), new Coordinate(1, 0),
                new Coordinate(-1, 0) };

        LinkedList<Step> bfs = new LinkedList<>();
        bfs.add(new Step(START, 0));
        Set<Coordinate> visited = new HashSet<>();

        while (!visited.contains(END) && !bfs.isEmpty()) {
            Step step = bfs.poll();
            for (Coordinate direction : directions) {
                exploreNeighbor(step, direction, bfs, visited, corruptedMemory);
            }
        }

        return bfs.isEmpty() ? -1 : bfs.getLast().stepCount;
    }

    private void exploreNeighbor(Step step, Coordinate direction, LinkedList<Step> bfs,
            Set<Coordinate> visited, boolean[][] corruptedMemory) {
        Coordinate neighbour = Coordinate.add(step.coordinate, direction);
        int y = neighbour.getY();
        int x = neighbour.getX();
        boolean isInBounds = x >= 0 && y >= 0 && x < corruptedMemory[0].length && y < corruptedMemory.length;
        if (isInBounds && !visited.contains(neighbour) && !corruptedMemory[y][x]) {
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
