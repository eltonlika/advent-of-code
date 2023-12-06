package aoc;

import java.util.Arrays;

public class Day6 {

    public static long solvePart1(final String content) {
        final String[] lines = content.split("\n");
        final long[] times = parseNumbers(lines[0].split(":")[1]);
        final long[] distances = parseNumbers(lines[1].split(":")[1]);
        long product = 1;
        for (int i = 0; i < times.length; i++) {
            final long solutions = numberOfSolutions(times[i], distances[i]);
            product *= solutions;
        }
        return product;
    }

    public static long solvePart2(final String content) {
        final String[] lines = content.split("\n");
        final long[] times = parseNumbers(lines[0].split(":")[1].replaceAll("\\s+", ""));
        final long[] distances = parseNumbers(lines[1].split(":")[1].replaceAll("\\s+", ""));
        long product = 1;
        for (int i = 0; i < times.length; i++) {
            final long solutions = numberOfSolutions(times[i], distances[i]);
            product *= solutions;
        }
        return product;
    }

    static long[] parseNumbers(final String line) {
        return Arrays.stream(line.trim().split("\\s+")).mapToLong(Long::parseLong).toArray();
    }

    static long numberOfSolutions(final long time, final long distance) {
        // Actually did the math here to find the formula for the solutions range.
        // The following relations must hold:
        // 1: Speed = Pressing time
        // 2: Total time = Pressing time + Moving time
        // 3: Distance = Speed * Moving time => Pressing time * (Total time - Pressing time)
        // If we want to break the record then this final relation must hold true and be solved:
        // Pressing time * (Total time - Pressing time) > Distance
        // shortened as: P * (T - P) > D
        // where T = Total time, D = Distance, P = Pressing time
        // solved as follows:
        // -P^2 + P*T > D
        // P^2 - P*T < -D
        // P^2 - P*T + T^2/4 < -D + T^2/4
        // (P - T/2)^2 < T^2/4 - D
        // -sqrt(T^2/4 - D) < P - T/2 < sqrt(T^2/4 - D)
        // T/2 - sqrt(T^2/4 - D) < P < T/2 + sqrt(T^2/4 - D)
        final double commonPart = Math.sqrt(Math.pow((double) time, 2.0) / 4.0 - (double) distance);
        final double lowerLimit = (double) time / 2.0 - commonPart;
        final double upperLimit = (double) time / 2.0 + commonPart;
        final long firstSolution =
                lowerLimit == Math.ceil(lowerLimit)
                        ? (long) lowerLimit + 1
                        : (long) Math.ceil(lowerLimit);
        final long lastSolution =
                upperLimit == Math.floor(upperLimit)
                        ? (long) upperLimit - 1
                        : (long) Math.floor(upperLimit);
        return lastSolution - firstSolution + 1;
    }
}
