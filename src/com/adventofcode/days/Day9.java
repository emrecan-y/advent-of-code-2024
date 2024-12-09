package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.adventofcode.AbstractAdventDay;

public class Day9 extends AbstractAdventDay {

    public Day9() {
        super(9);
    }

    public static void main(String[] args) {
        Day9 day = new Day9();
        day.partOne();
        day.partTwo();

    }

    @Override
    public void partOne() {
        List<Integer> fileSystem = getFileSystemInput();
        fragFileSystem(fileSystem);
        System.out.println(getCheckSum(fileSystem));
    }

    @Override
    public void partTwo() {
        List<Integer> fileSystem = getFileSystemInput();
        defragFileSystem(fileSystem);
        System.out.println(getCheckSum(fileSystem));
    }

    private List<Integer> getFileSystemInput() {
        char[] diskMap = super.getInput().get(0).toCharArray();

        List<Integer> fileSystem = new ArrayList<>();
        boolean isFile = true;
        int currentId = 0;

        for (char c : diskMap) {
            int count = Character.getNumericValue(c);
            Integer valueToAdd = isFile ? currentId : null;
            IntStream.range(0, count).forEach(i -> fileSystem.add(valueToAdd));
            currentId = isFile ? currentId + 1 : currentId;
            isFile = !isFile;
        }

        return fileSystem;
    }

    private List<Integer> fragFileSystem(List<Integer> fileSystem) {
        int lastIndex = 0;
        for (int i = fileSystem.size() - 1; i >= 0; i--) {
            if (fileSystem.get(i) != null) {

                // search for a empty space starting from 0
                for (int k = lastIndex; k < i; k++) {
                    if (fileSystem.get(k) == null) {
                        Collections.swap(fileSystem, i, k);
                        lastIndex = k;
                        break;
                    }
                }
            }
        }
        return fileSystem;
    }

    private List<Integer> defragFileSystem(List<Integer> fileSystem) {
        for (int i = fileSystem.size() - 1; i >= 0; i--) {
            Integer currentFile = fileSystem.get(i);
            if (currentFile != null) {
                int blockSize = getBlockSize(fileSystem, i);

                // search for a empty space big enough for currentfile
                for (int k = 0; k < i; k += blockSize) {
                    if (fileSystem.get(k) == null) {
                        if (getBlockSize(fileSystem, k) >= blockSize) {
                            swapBlock(fileSystem, i, k, blockSize);
                            break;
                        }
                    }
                }
            }
        }
        return fileSystem;
    }

    private int getBlockSize(List<Integer> fileSystem, int startIndex) {
        Integer blockValue = fileSystem.get(startIndex);
        int blockSize = 1;
        for (int i = startIndex - 1; i > 0 && fileSystem.get(i) == blockValue; i--) {
            blockSize++;
        }
        for (int i = startIndex + 1; i < fileSystem.size() && fileSystem.get(i) == blockValue; i++) {
            blockSize++;
        }
        return blockSize;
    }

    private void swapBlock(List<Integer> fileSystem, int b1, int b2, int blockSize) {
        b1 = getStartingIndexOfBlock(fileSystem, b1);
        b2 = getStartingIndexOfBlock(fileSystem, b2);
        for (int i = 0; i < blockSize; i++) {
            Collections.swap(fileSystem, b1 + i, b2 + i);
        }
    }

    private int getStartingIndexOfBlock(List<Integer> fileSystem, int b) {
        Integer blockValue = fileSystem.get(b);
        while (b > 0 && fileSystem.get(b - 1) == blockValue) {
            b--;
        }
        return b;
    }

    private long getCheckSum(List<Integer> fileSystem) {
        return IntStream.range(0, fileSystem.size())
                .mapToLong(i -> fileSystem.get(i) == null ? 0 : fileSystem.get(i) * i)
                .sum();
    }

    private void printFileSystem(List<Integer> fileSystem) {
        for (Integer num : fileSystem) {
            if (num == null) {
                System.out.print('.');
            } else {
                System.out.print(num);
            }
        }
        System.out.println();
    }

}
