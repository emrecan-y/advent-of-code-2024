package com.adventofcode.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.adventofcode.AbstractAdventDay;

public class Day11 extends AbstractAdventDay {

    public Day11() {
        super(11);
    }

    public static void main(String[] args) {
        Day11 day = new Day11();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        System.out.println(getStoneCountAfterBlinking(25));
    }

    @Override
    public void partTwo() {
        System.out.println(getStoneCountAfterBlinking(75));
    }

    private Long getStoneCountAfterBlinking(int blinkCount) {
        List<Long> stones = Stream.of(super.getInput().get(0).split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        Map<Long, Long> stoneIncidence = new HashMap<>();
        stones.forEach(s -> stoneIncidence.merge(s, 1L, Long::sum));

        for (int blink = 0; blink < blinkCount; blink++) {
            Map<Long, Long> nextStateStoneIncidence = new HashMap<>();
            for (Entry<Long, Long> entry : stoneIncidence.entrySet()) {
                List<Long> afterBlink = blink(entry.getKey());
                afterBlink.forEach(s -> nextStateStoneIncidence.merge(s, entry.getValue(), Long::sum));
            }
            stoneIncidence.clear();
            stoneIncidence.putAll(nextStateStoneIncidence);
        }

        return stoneIncidence.entrySet().stream().mapToLong(e -> e.getValue()).sum();
    }

    private List<Long> blink(Long stone) {
        List<Long> output = new ArrayList<>();
        String stoneString = String.valueOf(stone);
        if (stone == 0) {
            output.add(1L);
        } else if (stoneString.length() % 2 == 0) {
            int numSize = stoneString.length() / 2;
            Long firstHalf = Long.parseLong(stoneString.substring(0, numSize));
            Long secondHalf = Long.parseLong(stoneString.substring(numSize));
            output.add(firstHalf);
            output.add(secondHalf);
        } else {
            output.add(stone * 2024);
        }

        return output;
    }

}
