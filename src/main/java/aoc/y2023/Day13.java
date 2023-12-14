package aoc.y2023;

import aoc.AoC;

import java.util.Arrays;
import java.util.List;

public class Day13 implements AoC {

    private List<String> patterns;

    @Override
    public void load(String input) {
        this.patterns = Arrays.asList(input.split("\n\n"));
    }

    @Override
    public long getPart1Solution() {
        return patterns.stream().mapToLong(this::getReflection).sum();
    }

    @Override
    public long getPart2Solution() {
        return patterns.stream().mapToLong(this::getSmudgedReflection).sum();
    }

    private long getReflection(final String pattern) {
        final char[][] chars = pattern.lines().map(String::toCharArray).toArray(char[][]::new);

        // try each vertical line starting with the one between index 0 and 1
        vloop:
        for (int x = 1; x < chars[0].length; x++) {
            // check if every pair of equidistant columns from the vertical line is equal
            for (int offset = 0; x - offset - 1 >= 0 && x + offset < chars[0].length; offset++) {
                for (int y = 0; y < chars.length; y++) {
                    if (chars[y][x - offset - 1] != chars[y][x + offset]) {
                        continue vloop;
                    }
                }
            }
            return x;
        }

        // try each horizontal line starting with the one between index 0 and 1
        hloop:
        for (int y = 1; y < chars.length; y++) {
            // check if every pair of equidistant rows from the horizontal line is equal
            for (int offset = 0; y - offset - 1 >= 0 && y + offset < chars.length; offset++) {
                for (int x = 0; x < chars[y].length; x++) {
                    if (chars[y - offset - 1][x] != chars[y + offset][x]) {
                        continue hloop;
                    }
                }
            }
            return y * 100L;
        }

        return 0;
    }

    private long getSmudgedReflection(final String pattern) {
        final char[][] chars = pattern.lines().map(String::toCharArray).toArray(char[][]::new);

        // try each vertical line starting with the one between index 0 and 1
        for (int x = 1; x < chars[0].length; x++) {
            int differences = 0;
            for (int offset = 0;
                    differences <= 1 && x - offset - 1 >= 0 && x + offset < chars[0].length;
                    offset++) {
                // find the total of differences between every pair of column equidistant from
                // the vertical line
                for (int y = 0; differences <= 1 && y < chars.length; y++) {
                    if (chars[y][x - offset - 1] != chars[y][x + offset]) {
                        differences++;
                    }
                }
            }
            if (differences == 1) {
                return x;
            }
        }

        // try each horizontal line starting with the one between index 0 and 1
        for (int y = 1; y < chars.length; y++) {
            int differences = 0;
            for (int offset = 0;
                    differences <= 1 && y - offset - 1 >= 0 && y + offset < chars.length;
                    offset++) {
                // find the total of differences between every pair of column equidistant from
                // the horizontal line
                for (int x = 0; differences <= 1 && x < chars[y].length; x++) {
                    if (chars[y - offset - 1][x] != chars[y + offset][x]) {
                        differences++;
                    }
                }
            }
            if (differences == 1) {
                return y * 100L;
            }
        }

        return 0;
    }
}
