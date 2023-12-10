package aoc.y2023;

import aoc.AoC;
import aoc.util.Position2D;

import java.util.Objects;
import java.util.stream.LongStream;

public class Day3 implements AoC {

    private char[][] chars;

    @Override
    public void load(String input) {
        this.chars = input.lines().map(String::toCharArray).toArray(char[][]::new);
    }

    @Override
    public long getPart1Solution() {
        final boolean[][] collected = new boolean[this.chars.length][this.chars[0].length];
        return Position2D.generate(this.chars)
                .filter(pos -> isSymbol(pos.get(this.chars)))
                .mapToLong(pos -> getAdjacentNumbers(pos, collected).sum())
                .sum();
    }

    @Override
    public long getPart2Solution() {
        final boolean[][] collected = new boolean[this.chars.length][this.chars[0].length];
        return Position2D.generate(this.chars)
                .filter(pos -> pos.get(this.chars) == '*')
                .map(pos -> getAdjacentNumbers(pos, collected).toArray())
                .filter(arr -> arr.length == 2)
                .mapToLong(arr -> arr[0] * arr[1])
                .sum();
    }

    private LongStream getAdjacentNumbers(final Position2D pos, final boolean[][] collected) {
        return pos.adjacent(this.chars)
                .filter(adjacent -> !adjacent.get(collected))
                .map(adjacent -> findNumber(adjacent, collected))
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue);
    }

    private Long findNumber(final Position2D pos, final boolean[][] collected) {
        if (!Character.isDigit(pos.get(this.chars))) {
            return null;
        }

        // find start position of number
        int idx = pos.x();
        while (idx - 1 >= 0 && Character.isDigit(this.chars[pos.y()][idx - 1])) {
            idx--;
        }

        // read complete number and mark all it's positions as collected
        long num = 0;
        while (idx < this.chars[pos.y()].length && Character.isDigit(this.chars[pos.y()][idx])) {
            num = (num * 10) + Character.getNumericValue(this.chars[pos.y()][idx]);
            collected[pos.y()][idx] = true;
            idx++;
        }
        return num;
    }

    private boolean isSymbol(final char c) {
        return c != '.' && !Character.isDigit(c);
    }
}
