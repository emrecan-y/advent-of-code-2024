package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.IntStream;

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
		super.getInput().stream().map(s -> s.split("   ")).forEach(numbers -> {
			list1.add(Integer.parseInt(numbers[0]));
			list2.add(Integer.parseInt(numbers[1]));
		});
		list1.sort(Comparator.naturalOrder());
		list2.sort(Comparator.naturalOrder());
		int totalDistance = IntStream.range(0, list1.size())
				.map(i -> (Math.abs(list1.get(i) - list2.get(i))))
				.sum();
		System.out.println("The total distance in the list is " + totalDistance);
	}

	@Override
	public void partTwo() {
		ArrayList<Integer> list1 = new ArrayList<>();
		HashMap<Integer, Integer> mapForList2 = new HashMap<>();
		super.getInput().stream().map(s -> s.split("   ")).forEach(numbers -> {
			list1.add(Integer.parseInt(numbers[0]));
			mapForList2.merge(Integer.parseInt(numbers[1]), 1, Integer::sum);
		});
		int similarityScore = list1.stream()
				.mapToInt(num -> num * mapForList2.getOrDefault(num, 0))
				.sum();
		System.out.println("The similarity score is " + similarityScore);
	}

}
