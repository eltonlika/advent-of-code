package aoc.y2023;

import static aoc.util.Util.parseNumbers;

import aoc.AoC;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 implements AoC {

    private List<RecordLine> recordLines1;
    private List<RecordLine> recordLines2;
    private Map<String, Long> cache;

    private String cacheKey(final int springIndex, final int groupIndex) {
        return String.valueOf(springIndex) + "," + String.valueOf(groupIndex);
    }

    private long getArrangements(final RecordLine recordLine) {
        cache = new HashMap<>();
        return getArrangements(recordLine.springs(), recordLine.groups(), 0, 0);
    }

    private long getArrangements(
            final char[] springs,
            final int[] groups,
            final int springIndex,
            final int groupIndex) {

        if (cache.containsKey(cacheKey(springIndex, groupIndex))) {
            return cache.get(cacheKey(springIndex, groupIndex));
        }

        final int group = groups[groupIndex];
        long possibleArrangements = 0;

        for (int i = springIndex; i + group - 1 < springs.length; i++) {

            // should not skip springs - all of them should be allocated to groups
            if (i >= 1 && springs[i - 1] == '#') {
                break;
            }

            // group should not contain '.'
            if (IntStream.range(i, i + group).anyMatch(idx -> springs[idx] == '.')) {
                continue;
            }

            // group should not be neighboured by springs
            if (i + group < springs.length && springs[i + group] == '#') {
                continue;
            }

            // remaining springs should not be more than the sum of remaining groups
            if (IntStream.range(i + group, springs.length).filter(idx -> springs[idx] == '#').count() //
                    > Arrays.stream(groups, groupIndex + 1, groups.length).sum()) {
                continue;
            }

            if (groupIndex == groups.length - 1) {
                possibleArrangements++;
                continue;
            }

            possibleArrangements += getArrangements(springs, groups, i + group + 1, groupIndex + 1);
        }

        cache.put(cacheKey(springIndex, groupIndex), possibleArrangements);

        return possibleArrangements;
    }

    @Override
    public void load(String input) {
        this.recordLines1 = input.lines().map(RecordLine::parse).toList();
        this.recordLines2 = input.lines().map(line -> {
            final String[] split = line.split(" ");
            return RecordLine.parse(
                    IntStream.range(0, 5).boxed().map(i -> split[0]).collect(Collectors.joining("?"))
                            + " "
                            + IntStream.range(0, 5).boxed().map(i -> split[1]).collect(Collectors.joining(",")));
        }).toList();
    }

    @Override
    public long getPart1Solution() {
        return recordLines1.stream().mapToLong(this::getArrangements).sum();
    }

    @Override
    public long getPart2Solution() {
        return recordLines2.stream().mapToLong(this::getArrangements).sum();
    }

    private record RecordLine(char[] springs, int[] groups) {

        public static RecordLine parse(final String line) {
            final String[] split = line.split(" ");
            return new RecordLine(split[0].toCharArray(),
                    parseNumbers(split[1], ",").boxed().mapToInt(Long::intValue).toArray());
        }
    }
}
