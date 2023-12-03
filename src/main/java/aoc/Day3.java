package aoc;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day3 {

    static final int[][] adjacentMovements =
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

    public static int solvePart1(final String content) {
        final char[][] chars = content.lines().map(String::toCharArray).toArray(char[][]::new);
        int sum = 0;
        for (int yPos = 0; yPos < chars.length; yPos++) {
            for (int xPos = 0; xPos < chars[yPos].length; xPos++) {
                if (isSymbol(chars[yPos][xPos])) {
                    sum += getAdjacentNumbers(chars, xPos, yPos).sum();
                }
            }
        }
        return sum;
    }

    public static int solvePart2(final String content) {
        final char[][] chars = content.lines().map(String::toCharArray).toArray(char[][]::new);
        int sum = 0;
        for (int yPos = 0; yPos < chars.length; yPos++) {
            for (int xPos = 0; xPos < chars[yPos].length; xPos++) {
                if (chars[yPos][xPos] == '*') {
                    int[] nums = getAdjacentNumbers(chars, xPos, yPos).toArray();
                    if (nums.length == 2) {
                        sum += nums[0] * nums[1];
                    }
                }
            }
        }
        return sum;
    }

    static IntStream getAdjacentNumbers(final char[][] chars, final int xPos, final int yPos) {
        return Arrays.stream(adjacentMovements)
                .map(movement -> findAndClearNumber(chars, xPos + movement[0], yPos + movement[1]))
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue);
    }

    static Integer findAndClearNumber(final char[][] chars, final int xPos, final int yPos) {
        if (yPos < 0
                || yPos >= chars.length
                || xPos < 0
                || xPos >= chars[yPos].length
                || !Character.isDigit(chars[yPos][xPos])) {
            return null;
        }

        // find number start position
        int startPos = xPos;
        while (startPos - 1 >= 0 && Character.isDigit(chars[yPos][startPos - 1])) {
            startPos--;
        }

        // read complete number to the end
        int num = 0;
        for (int i = startPos; i < chars[yPos].length && Character.isDigit(chars[yPos][i]); i++) {
            num = (num * 10) + Character.getNumericValue(chars[yPos][i]);
            chars[yPos][i] = '.'; // clear out number so it is not read 2 times
        }
        return num;
    }

    static boolean isSymbol(final char c) {
        return c != '.' && !Character.isDigit(c);
    }
}
