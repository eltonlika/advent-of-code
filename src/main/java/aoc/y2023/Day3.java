package aoc.y2023;

import aoc.AoC;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.LongStream;

public class Day3 implements AoC {

    private static final int[][] adjacentDirections =
            new int[][] {
                {-1, -1},
                {0, -1},
                {1, -1},
                {-1, 0},
                {1, 0},
                {-1, 1},
                {0, 1},
                {1, 1}
            };

    private char[][] chars;

    @Override
    public void load(String input) {
        this.chars = input.lines().map(String::toCharArray).toArray(char[][]::new);
    }

    @Override
    public long getPart1Solution() {
        long sum = 0L;
        final boolean[][] collected = new boolean[this.chars.length][this.chars[0].length];

        for (int yPos = 0; yPos < this.chars.length; yPos++) {
            for (int xPos = 0; xPos < this.chars[yPos].length; xPos++) {
                if (!isSymbol(this.chars[yPos][xPos])) {
                    continue;
                }
                sum += getAdjacentNumbers(xPos, yPos, collected).sum();
            }
        }

        return sum;
    }

    @Override
    public long getPart2Solution() {
        long sum = 0L;
        final boolean[][] collected = new boolean[this.chars.length][this.chars[0].length];

        for (int yPos = 0; yPos < this.chars.length; yPos++) {
            for (int xPos = 0; xPos < this.chars[yPos].length; xPos++) {
                if (this.chars[yPos][xPos] != '*') {
                    continue;
                }
                final long[] adjacentNumbers = getAdjacentNumbers(xPos, yPos, collected).toArray();
                if (adjacentNumbers.length != 2) {
                    continue;
                }
                sum += adjacentNumbers[0] * adjacentNumbers[1];
            }
        }

        return sum;
    }

    private LongStream getAdjacentNumbers(
            final int xPos, final int yPos, final boolean[][] collected) {
        return Arrays.stream(adjacentDirections)
                .map(dir -> findNumber(xPos + dir[0], yPos + dir[1], collected))
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue);
    }

    private Long findNumber(final int xPos, final int yPos, final boolean[][] collected) {
        if (yPos < 0
                || yPos >= this.chars.length
                || xPos < 0
                || xPos >= this.chars[yPos].length
                || !Character.isDigit(this.chars[yPos][xPos])
                || collected[yPos][xPos]) {
            return null;
        }

        // find start position of number
        int idx = xPos;
        while (idx - 1 >= 0 && Character.isDigit(this.chars[yPos][idx - 1])) {
            idx--;
        }

        // read complete number and mark all it's positions as collected
        long num = 0;
        while (idx < this.chars[yPos].length && Character.isDigit(this.chars[yPos][idx])) {
            num = (num * 10) + Character.getNumericValue(this.chars[yPos][idx]);
            collected[yPos][idx] = true;
            idx++;
        }
        return num;
    }

    private boolean isSymbol(final char c) {
        return c != '.' && !Character.isDigit(c);
    }
}
