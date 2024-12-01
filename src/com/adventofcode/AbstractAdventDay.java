package com.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAdventDay {
	private int dayNumber;
	private List<String> input = new ArrayList<>();;

	public AbstractAdventDay(int dayNumber) {
		this.dayNumber = dayNumber;
		readInput();
	}

	public abstract void partOne();

	public abstract void partTwo();

	public List<String> getInput() {
		return input;
	}

	private void readInput() {
		Path inputFilePath = Paths.get("./inputs/day" + this.dayNumber + ".txt");
		if (Files.exists(inputFilePath)) {
			input = parseFile(inputFilePath);
			if (input.isEmpty()) {
				System.out.println("Input file for day " + this.dayNumber + " is empty.");
			}
		} else {
			System.out.println("Input file for day " + this.dayNumber + " doesn't exist.");
		}
	}

	private List<String> parseFile(Path path) {
		List<String> fileContent = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String currentLine = "";
			while ((currentLine = reader.readLine()) != null) {
				fileContent.add(currentLine);
			}
		} catch (IOException ioe) {
			System.out.println("Something went wrong reading the input file from day " + this.dayNumber);
		}
		return fileContent;
	}
}
