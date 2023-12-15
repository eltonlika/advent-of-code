package aoc.y2023;

import aoc.AoC;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 implements AoC {

    private char[][] grid;

    private static long getTiltedNorthLoad(final char[][] chars) {
        return IntStream.range(0, chars[0].length)
                .mapToLong(x -> getTiltedColumnLoad(chars, x))
                .sum();
    }

    private static long getNorthLoad(final char[][] chars) {
        return IntStream.range(0, chars.length)
                .mapToLong(
                        y ->
                                IntStream.range(0, chars[y].length)
                                        .mapToLong(x -> chars[y][x] == 'O' ? chars.length - y : 0)
                                        .sum())
                .sum();
    }

    private static long getTiltedColumnLoad(final char[][] chars, final int column) {
        long load = 0;
        for (int fillPosition = 0, y = 0; y < chars.length; y++) {
            switch (chars[y][column]) {
                case '#' -> fillPosition = y + 1;
                case 'O' -> {
                    load += chars.length - fillPosition;
                    fillPosition++;
                }
            }
        }
        return load;
    }

    private static char[][] rotateClockwise(final char[][] chars) {
        final char[][] result = new char[chars[0].length][chars.length];
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[0].length; x++) {
                result[x][result[0].length - y - 1] = chars[y][x];
            }
        }
        return result;
    }

    private static char[][] tiltEast(final char[][] chars) {
        return Arrays.stream(chars, 0, chars.length)
                .map(String::valueOf)
                .map(
                        line ->
                                Arrays.stream(line.split("#"))
                                        .map(
                                                group -> {
                                                    final char[] cs = group.toCharArray();
                                                    Arrays.sort(cs);
                                                    return String.valueOf(cs);
                                                })
                                        .collect(Collectors.joining("#")))
                .map( // pad missing '#' at end of string
                        line ->
                                line.length() == chars[0].length
                                        ? line
                                        : line
                                                + IntStream.range(
                                                                0, chars[0].length - line.length())
                                                        .boxed()
                                                        .map(i -> "#")
                                                        .collect(Collectors.joining()))
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    @Override
    public void load(String input) {
        this.grid = input.lines().map(String::toCharArray).toArray(char[][]::new);
    }

    @Override
    public long getPart1Solution() {
        return getTiltedNorthLoad(grid);
    }

    @Override
    public long getPart2Solution() {
        final Map<String, Integer> loadPerCycle = new HashMap<>();
        int firstCycle = 0;
        int repeatingCycle = 0;

        char[][] copy =
                Arrays.stream(grid)
                        .map(arr -> Arrays.copyOf(arr, arr.length))
                        .toArray(char[][]::new);

        for (int cycle = 1; cycle <= 1_000_000_000; cycle++) {
            for (int tiltSide = 1; tiltSide <= 4; tiltSide++) {
                copy = tiltEast(rotateClockwise(copy));
            }

            var s = Arrays.stream(copy).map(String::valueOf).collect(Collectors.joining("\n"));
            if (loadPerCycle.containsKey(s)) {
                firstCycle = loadPerCycle.get(s);
                repeatingCycle = cycle;
                break;
            }

            loadPerCycle.put(s, cycle);
        }

        final int cycleLength = repeatingCycle - firstCycle;
        final int resultCycle = firstCycle + ((1_000_000_000 - firstCycle) % cycleLength);

        return loadPerCycle.entrySet().stream()
                .filter(e -> e.getValue() == resultCycle)
                .map(
                        e ->
                                getNorthLoad(
                                        e.getKey()
                                                .lines()
                                                .map(String::toCharArray)
                                                .toArray(char[][]::new)))
                .findFirst()
                .orElseThrow();
    }
}
