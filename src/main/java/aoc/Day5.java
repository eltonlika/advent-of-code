package aoc;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;

public class Day5 {

    long[] seeds;
    Mapping seedToSoil;
    Mapping soilToFertilizer;
    Mapping fertilizerToWater;
    Mapping waterToLight;
    Mapping lightToTemperature;
    Mapping temperatureToHumidity;
    Mapping humidityToLocation;

    void parse(final String content) {
        final String[] blocks = content.split("\n\n");
        seeds = parseNumbers(blocks[0].split(":")[1]);
        seedToSoil = parseMapping(blocks[1]);
        soilToFertilizer = parseMapping(blocks[2]);
        fertilizerToWater = parseMapping(blocks[3]);
        waterToLight = parseMapping(blocks[4]);
        lightToTemperature = parseMapping(blocks[5]);
        temperatureToHumidity = parseMapping(blocks[6]);
        humidityToLocation = parseMapping(blocks[7]);
    }

    public long solvePart1(final String content) {
        parse(content);
        return Arrays.stream(seeds)
                .map(seedToSoil::map)
                .map(soilToFertilizer::map)
                .map(fertilizerToWater::map)
                .map(waterToLight::map)
                .map(lightToTemperature::map)
                .map(temperatureToHumidity::map)
                .map(humidityToLocation::map)
                .min()
                .orElseThrow();
    }

    public long solvePart2(final String content) {
        parse(content);

        LongStream seedsPart2 = LongStream.of();
        for (int i = 0; i < seeds.length; i += 2) {
            seedsPart2 =
                    LongStream.concat(
                            seedsPart2, LongStream.range(seeds[i], seeds[i] + seeds[i + 1]));
        }

        return seedsPart2
                .map(seedToSoil::map)
                .map(soilToFertilizer::map)
                .map(fertilizerToWater::map)
                .map(waterToLight::map)
                .map(lightToTemperature::map)
                .map(temperatureToHumidity::map)
                .map(humidityToLocation::map)
                .min()
                .orElseThrow();
    }

    public long solvePart2Optimized(final String content) {
        parse(content);

        final List<Range> seedsPart2 = new ArrayList<>();
        for (int i = 0; i < seeds.length; i += 2) {
            seedsPart2.add(new Range(seeds[i], seeds[i] + seeds[i + 1] - 1));
        }

        return seedsPart2.stream()
                .flatMap(seed -> mapToLocation(seed).stream())
                .mapToLong(Range::start)
                .min()
                .orElseThrow();
    }

    public List<Range> mapToLocation(final Range seed) {
        List<Range> seed_ranges = List.of(seed);
        for (var mapping :
                List.of(
                        seedToSoil,
                        soilToFertilizer,
                        fertilizerToWater,
                        waterToLight,
                        lightToTemperature,
                        temperatureToHumidity,
                        humidityToLocation)) {
            seed_ranges =
                    seed_ranges.stream().flatMap(sr -> mapRange(sr, mapping).stream()).toList();
        }
        return seed_ranges;
    }

    public List<Range> mapRange(final Range range, final Mapping mapping) {
        final List<Range> result = new ArrayList<>();
        List<Range> remainingAfterIntersection = new ArrayList<>(List.of(range));
        for (final MappingEntry mapEntry : mapping.mappings()) {
            final Range intersection = mapEntry.source().intersection(range);
            if (intersection != null) {
                result.add(intersection.translate(mapEntry.source(), mapEntry.destination()));
                remainingAfterIntersection =
                        remainingAfterIntersection.stream()
                                .flatMap(e -> e.subtract(intersection).stream())
                                .toList();
            }
        }
        if (!result.isEmpty()) {
            result.addAll(remainingAfterIntersection);
        }
        return result.isEmpty() ? List.of(range) : result;
    }

    long[] parseNumbers(final String line) {
        return Arrays.stream(line.trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
    }

    Mapping parseMapping(final String block) {
        return new Mapping(
                block.split(":")[1]
                        .lines()
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .map(this::parseNumbers)
                        .sorted(Comparator.comparingLong(nums -> nums[1]))
                        .map(
                                nums ->
                                        new MappingEntry(
                                                new Range(nums[1], nums[1] + nums[2] - 1),
                                                new Range(nums[0], nums[0] + nums[2] - 1)))
                        .toList());
    }

    public record Range(long start, long end) {

        public Range intersection(final Range other) {
            if (other.start > this.end || this.start > other.end) {
                return null; // non-overlapping ranges
            }
            return new Range(max(this.start, other.start), min(this.end, other.end));
        }

        public List<Range> subtract(final Range other) {
            final List<Range> remaining = new ArrayList<>(2);
            if (this.start < other.start) {
                remaining.add(new Range(this.start, other.start - 1));
            }
            if (this.end > other.end) {
                remaining.add(new Range(other.end + 1, this.end));
            }
            return remaining;
        }

        public Range translate(final Range source, final Range destination) {
            return new Range(
                    destination.start - source.start + this.start,
                    destination.start - source.start + this.end);
        }
    }

    public record MappingEntry(Range source, Range destination) {}

    public record Mapping(List<MappingEntry> mappings) {
        public long map(long value) {
            MappingEntry mapping = null;
            for (final MappingEntry e : mappings) {
                if (value <= e.source.end) {
                    mapping = e;
                    break;
                }
            }
            if (mapping != null && value >= mapping.source.start) {
                final long diff = value - mapping.source.start;
                return mapping.destination.start + diff;
            }
            return value;
        }
    }
}
