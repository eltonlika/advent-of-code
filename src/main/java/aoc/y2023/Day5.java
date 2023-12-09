package aoc.y2023;

import static aoc.util.Util.parseNumbers;

import static java.lang.Math.max;
import static java.lang.Math.min;

import aoc.AoC;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 implements AoC {

    private long[] seeds;
    private List<Mapping> mappings;

    @Override
    public void load(String input) {
        final String[] blocks = input.split("\n\n");
        this.seeds = parseNumbers(blocks[0].split(":")[1], " ").toArray();
        this.mappings = Arrays.stream(blocks, 1, blocks.length).map(Mapping::parse).toList();
    }

    @Override
    public long getPart1Solution() {
        return Arrays.stream(this.seeds).map(this::mapToLocation).min().orElseThrow();
    }

    @Override
    public long getPart2Solution() {
        return IntStream.range(0, this.seeds.length)
                .filter(i -> i % 2 == 0)
                .mapToObj(i -> new Range(this.seeds[i], this.seeds[i] + this.seeds[i + 1] - 1))
                .flatMap(this::mapToLocation)
                .mapToLong(Range::start)
                .min()
                .orElseThrow();
    }

    private long mapToLocation(final long seed) {
        long current = seed;
        for (final Mapping mapping : this.mappings) {
            current = mapping.map(current);
        }
        return current;
    }

    private Stream<Range> mapToLocation(final Range seed) {
        Stream<Range> current = Stream.of(seed);
        for (final Mapping mapping : this.mappings) {
            current = current.flatMap(mapping::map);
        }
        return current;
    }

    private record Range(long start, long end) {

        Range {
            if (start > end) {
                throw new IllegalArgumentException("start should not be after end");
            }
        }

        Range intersection(final Range other) {
            return other.end() < this.start() || other.start() > this.end()
                    ? null
                    : new Range(max(other.start(), this.start()), min(other.end(), this.end()));
        }

        Range diffLeft(final Range intersection) {
            return this.start() >= intersection.start()
                    ? null
                    : new Range(this.start(), intersection.start() - 1);
        }

        Range diffRight(final Range intersection) {
            return this.end() <= intersection.end()
                    ? null
                    : new Range(intersection.end() + 1, this.end());
        }

        Range map(final Range from, final Range to) {
            final long diff = to.start() - from.start();
            return new Range(this.start() + diff, this.end() + diff);
        }
    }

    private record MappingEntry(Range source, Range destination) {

        static MappingEntry parse(final String line) {
            final long[] nums = parseNumbers(line, " ").toArray();
            return new MappingEntry(
                    new Range(nums[1], nums[1] + nums[2] - 1),
                    new Range(nums[0], nums[0] + nums[2] - 1));
        }
    }

    private record Mapping(List<MappingEntry> entries) {

        static Mapping parse(final String block) {
            return new Mapping(
                    block.split(":")[1]
                            .lines()
                            .map(String::trim)
                            .filter(s -> !s.isBlank())
                            .map(MappingEntry::parse)
                            .sorted(Comparator.comparingLong(e -> e.source().start()))
                            .toList());
        }

        long map(final long value) {
            return entries().stream()
                    .filter(e -> value >= e.source().start() && value <= e.source().end())
                    .findFirst()
                    .map(e -> value + e.destination().start() - e.source().start())
                    .orElse(value);
        }

        Stream<Range> map(final Range range) {
            Stream<Range> result = Stream.empty();
            boolean anyIntersectionFound = false;
            Range current = range;

            for (final MappingEntry entry : entries()) {
                if (current == null) {
                    break;
                }

                final Range intersection = entry.source().intersection(current);
                if (intersection == null) {
                    continue;
                }
                anyIntersectionFound = true;

                final Range remainingLeft = current.diffLeft(intersection);
                final Range mapped = intersection.map(entry.source(), entry.destination());
                result = Stream.concat(result, Stream.ofNullable(remainingLeft));
                result = Stream.concat(result, Stream.of(mapped));
                current = current.diffRight(intersection);
            }

            return anyIntersectionFound ? result : Stream.ofNullable(range);
        }
    }
}
