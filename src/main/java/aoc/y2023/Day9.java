package aoc.y2023;

import static aoc.util.Util.parseNumbers;

import aoc.AoC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 implements AoC {

    private List<long[]> lines;

    @Override
    public void load(String input) {
        this.lines = input.lines().map(line -> parseNumbers(line, " ").toArray()).toList();
    }

    @Override
    public long getPart1Solution() {
        return this.lines.stream().mapToLong(this::predictNextValue).sum();
    }

    @Override
    public long getPart2Solution() {
        return this.lines.stream().mapToLong(this::predictPreviousValue).sum();
    }

    private List<long[]> diffs(final long[] numbers) {
        final List<long[]> result = new ArrayList<>(List.of(numbers));
        while (Arrays.stream(result.getLast()).anyMatch(n -> n != 0L)) {
            final long[] current = result.getLast();
            final long[] diffs = new long[current.length - 1];
            for (int i = 0; i < diffs.length; i++) {
                diffs[i] = current[i + 1] - current[i];
            }
            result.add(diffs);
        }
        return result;
    }

    private long predictNextValue(final long[] numbers) {
        return diffs(numbers).stream().mapToLong(a -> a[a.length - 1]).sum();
    }

    private long predictPreviousValue(final long[] numbers) {
        return diffs(numbers).reversed().stream().mapToLong(a -> a[0]).reduce(0L, (a, b) -> b - a);
    }
}
