package com.adventofcode.days;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day16 extends AbstractAdventDay {

    private final static char START = 'S';
    private final static char END = 'E';
    private final static char WALL = '#';
    private final static char FREE_SPACE = '.';

    public Day16() {
        super(16);
    }

    public static void main(String[] args) {
        Day16 day = new Day16();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        List<List<MapTile>> map2d = parseMap();

        Coordinate endPos = getPosOfChar(map2d, END);
        Coordinate startPos = getPosOfChar(map2d, START);
        map2d.get(startPos.getY()).get(startPos.getX()).direction = '>';
        map2d.get(startPos.getY()).get(startPos.getX()).score = 0L;

        System.out.println(getLowestPossibleScore(map2d, startPos, endPos));

    }

    @Override
    public void partTwo() {
    }

    private long getLowestPossibleScore(List<List<MapTile>> map2d, Coordinate startPos, Coordinate endPos) {
        Queue<MapTile> bfs = new LinkedList<>();

        bfs.add(map2d.get(startPos.getY()).get(startPos.getX()));

        char[] moves = { '^', '>', 'v', '<' };

        while (!bfs.isEmpty()) {
            MapTile tile = bfs.poll();
            Coordinate tilePos = tile.coordinate;

            for (char move : moves) {
                Coordinate moveValue = getDirectionValue(move);
                Character tileDirection = tile.direction;
                long turnScore = 0;
                if (tileDirection != move) {
                    turnScore = getScoreAfterTurn(tileDirection, move);
                    tileDirection = move;
                }
                MapTile nextTile = map2d.get(tilePos.getY() + moveValue.getY()).get(tilePos.getX() + moveValue.getX());
                long newTileScore = tile.score + 1 + turnScore;
                if (nextTile.content != WALL && newTileScore < nextTile.score) {
                    nextTile.score = newTileScore;
                    nextTile.direction = tileDirection;
                    bfs.add(nextTile);
                }
            }
        }
        return map2d.get(endPos.getY()).get(endPos.getX()).score;

    }

    private Coordinate getDirectionValue(char direction) {
        switch (direction) {
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

    private long getScoreAfterTurn(char tileDirection, char moveDirection) {
        List<Character> turnList = List.of(new Character[] { '^', '>', 'v', '<' });
        int delta = Math.abs(turnList.indexOf(tileDirection) - turnList.indexOf(moveDirection));
        if (delta > 2) {
            return (delta - 2) * 1000;
        } else {
            return delta * 1000;
        }
    }

    private Coordinate getPosOfChar(List<List<MapTile>> map2d, Character c) {
        for (int y = 0; y < map2d.size(); y++) {
            for (int x = 0; x < map2d.get(y).size(); x++) {
                if (map2d.get(y).get(x).content == c) {
                    return new Coordinate(x, y);
                }
            }
        }
        return null;
    }

    private List<List<MapTile>> parseMap() {
        List<List<MapTile>> map2d = new ArrayList<>();
        List<String> input = super.getInput();
        for (int y = 0; y < input.size(); y++) {
            List<MapTile> line = new ArrayList<>();
            for (int x = 0; x < input.get(y).length(); x++) {
                line.add(new MapTile(x, y, input.get(y).charAt(x)));
            }
            map2d.add(line);
        }
        return map2d;
    }

    private class MapTile {
        Coordinate coordinate;
        Character content;
        long score = Long.MAX_VALUE;
        Character direction;

        public MapTile(int x, int y, Character content) {
            this.coordinate = new Coordinate(x, y);
            this.content = content;
            this.direction = null;
        }
    }

}
