package com.adventofcode.days;

import java.util.ArrayList;
import java.util.HashMap;

import com.adventofcode.AbstractAdventDay;

public class Day1 extends AbstractAdventDay {

	public Day1() {
		super(1);
	}

	public static void main(String[] args) {
		Day1 day = new Day1();
		day.partOne();
		day.partTwo();

	}

	@Override
	public void partOne() {
		ArrayList<Integer> list1 = new ArrayList<>();
		ArrayList<Integer> list2 = new ArrayList<>();
		for (String s : super.getInput()) {
			String[] numbers = s.split("   ");
			list1.add(Integer.parseInt(numbers[0]));
			list2.add(Integer.parseInt(numbers[1]));
		}
		list1.sort((a, b) -> Integer.compare(a, b));
		list2.sort((a, b) -> Integer.compare(a, b));
		long result = 0L;
		for (int i = 0; i < list1.size(); i++) {
			result += (Math.abs(list1.get(i) - list2.get(i)));
		}
		System.out.println("The total distance in the list is " + result);
	}

	@Override
	public void partTwo() {
		ArrayList<Integer> list1 = new ArrayList<>();
		HashMap<Integer, Integer> mapForList2 = new HashMap<>();
		for (String s : super.getInput()) {
			String[] numbers = s.split("   ");
			list1.add(Integer.parseInt(numbers[0]));
			Integer secondListNumber = Integer.parseInt(numbers[1]);
			if (mapForList2.containsKey(secondListNumber)) {
				mapForList2.put(secondListNumber, mapForList2.get(secondListNumber) + 1);
			} else {
				mapForList2.put(secondListNumber, 1);
			}
		}
		long result = 0L;
		for (Integer currentNumber : list1) {
			Integer numberCount = mapForList2.get(currentNumber);
			if (numberCount == null) {
				continue;
			}
			long similarityScore = currentNumber * numberCount;
			result += similarityScore;
		}
		System.out.println("The similarity score  is " + result);
	}

}
