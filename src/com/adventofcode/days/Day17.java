package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.AbstractAdventDay;

public class Day17 extends AbstractAdventDay {

    public Day17() {
        super(17);
    }

    public static void main(String[] args) {
        Day17 day = new Day17();
        day.partOne();
        day.partTwo();

    }

    private long registerA;
    private long registerB;
    private long registerC;

    @Override
    public void partOne() {
        initRegisters();
        List<Integer> programList = getProgramList();
        List<Integer> output = getProgramOutput(programList, false);
        printOutput(output);
    }

    @Override
    public void partTwo() {
        // brute force doesnt work, toooo slow
        // but dont want to spend more time on this today
        long count = 0;
        List<Integer> programList = getProgramList();
        while (true) {
            registerA = count;
            registerB = 0;
            registerC = 0;
            List<Integer> output = getProgramOutput(programList, true);
            if (programList.equals(output)) {
                printOutput(output);
                printOutput(programList);
                break;
            }
            count++;
        }
        System.out.println(count);
    }

    private void initRegisters() {
        List<String> input = super.getInput();
        registerA = Integer.parseInt(input.get(0).split(": ")[1]);
        registerB = Integer.parseInt(input.get(1).split(": ")[1]);
        registerC = Integer.parseInt(input.get(2).split(": ")[1]);
    }

    private List<Integer> getProgramList() {
        String line = super.getInput().get(4);
        return Stream.of(line.split(": ")[1].split(",")).map(Integer::parseInt).toList();
    }

    private List<Integer> getProgramOutput(List<Integer> programList, boolean checkOutputLength) {
        List<Integer> output = new ArrayList<>();
        for (int i = 0; i < programList.size(); i += 2) {
            if (checkOutputLength && output.size() >= programList.size()) {
                break;
            }
            long operand = programList.get(i + 1);
            switch (programList.get(i)) {
                case 0:
                    registerA = registerA / (long) Math.pow(2, getComboOperand(operand));
                    break;
                case 1:
                    registerB = registerB ^ operand;
                    break;
                case 2:
                    registerB = getComboOperand(operand) % 8;
                    break;
                case 3:
                    i = registerA != 0 ? (int) operand - 2 : i;
                    break;
                case 4:
                    registerB = registerB ^ registerC;
                    break;
                case 5:
                    output.add((int) getComboOperand(operand) % 8);
                    break;
                case 6:
                    registerB = registerA / (long) Math.pow(2, getComboOperand(operand));
                    break;
                case 7:
                    registerC = registerA / (long) Math.pow(2, getComboOperand(operand));
                    break;
            }
        }
        return output;
    }

    private long getComboOperand(long operand) {
        if (operand < 4) {
            return operand;
        } else if (operand == 4L) {
            return registerA;
        } else if (operand == 5L) {
            return registerB;
        } else if (operand == 6L) {
            return registerC;
        } else {
            return -1;
        }
    }

    private void printOutput(List<Integer> output) {
        System.out.println(Arrays.toString(output.toArray()).replace(" ", "")
                .replaceAll("[\\[\\]]", ""));
    }

}
