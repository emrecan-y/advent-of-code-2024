package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;

import com.adventofcode.AbstractAdventDay;
import com.adventofcode.misc.Coordinate;

public class Day13 extends AbstractAdventDay {

    public Day13() {
        super(13);
    }

    public static void main(String[] args) {
        Day13 day = new Day13();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        System.out.println(getTokenCount(false));
    }

    @Override
    public void partTwo() {
        System.out.println(getTokenCount(true));

    }

    private long getTokenCount(boolean unitConversionCompensation) {
        // Base equations
        // Px = Ax * aPressCount + Bx * bPressCount
        // Py = Ay * aPressCount + By * bPressCount
        // i had to get some help to rearrange these for PressCounts
        //
        // aPressCount = (By * Px - Bx * Py) / (Ax * By - Ay * Bx)
        // bPressCount = (Ax * Py -Ay * Px) / (Ax * By - Ay * Bx)

        List<ClawMachine> clawMachines = getClawMachines();

        long totalTokens = 0;
        for (ClawMachine cm : clawMachines) {
            Coordinate a = cm.buttonA;
            Coordinate b = cm.buttonB;
            long pY = unitConversionCompensation ? cm.prizeLocationY + 10000000000000L : cm.prizeLocationY;
            long pX = unitConversionCompensation ? cm.prizeLocationX + 10000000000000L : cm.prizeLocationX;

            long buttonACount = (b.getY() * pX - b.getX() * pY);
            long buttonBCount = (pY * a.getX() - a.getY() * pX);
            int divisor = (a.getX() * b.getY() - a.getY() * b.getX());

            if (buttonACount % divisor == 0 && buttonBCount % divisor == 0) {
                long tokens = buttonACount / divisor * 3 + buttonBCount / divisor;
                totalTokens += tokens;
            }

        }
        return totalTokens;
    }

    private List<ClawMachine> getClawMachines() {
        List<String> input = super.getInput();
        List<ClawMachine> clawMachines = new ArrayList<>();

        for (int i = 0; i < input.size(); i += 4) {
            ClawMachine cm = new ClawMachine();
            cm.buttonA = parseCoordinate(input.get(i));
            cm.buttonB = parseCoordinate(input.get(i + 1));
            Coordinate prize = parseCoordinate(input.get(i + 2));
            cm.prizeLocationX = (long) prize.getX();
            cm.prizeLocationY = (long) prize.getY();

            clawMachines.add(cm);
        }
        return clawMachines;
    }

    private Coordinate parseCoordinate(String s) {
        String x = s.substring(s.indexOf("X") + 2, s.indexOf(","));
        String y = s.substring(s.indexOf("Y") + 2);
        return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
    }

    private class ClawMachine {
        Coordinate buttonA;
        Coordinate buttonB;
        Long prizeLocationX;
        Long prizeLocationY;
    }

}
