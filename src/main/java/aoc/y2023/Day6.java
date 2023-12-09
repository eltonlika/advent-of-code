package aoc.y2023;

import static aoc.util.Util.parseNumbers;

import aoc.AoC;

public class Day6 implements AoC {

    private long[] timesPart1;
    private long[] distancesPart1;
    private long[] timesPart2;
    private long[] distancesPart2;

    @Override
    public void load(String input) {
        final String[] lines = input.split("\n");
        this.timesPart1 = parseNumbers(lines[0].split(":")[1], " ").toArray();
        this.distancesPart1 = parseNumbers(lines[1].split(":")[1], " ").toArray();
        this.timesPart2 =
                parseNumbers(lines[0].split(":")[1].replaceAll("\\s+", ""), " ").toArray();
        this.distancesPart2 =
                parseNumbers(lines[1].split(":")[1].replaceAll("\\s+", ""), " ").toArray();
    }

    @Override
    public long getPart1Solution() {
        long product = 1L;
        for (int i = 0; i < this.timesPart1.length; i++) {
            product *= numberOfSolutions(this.timesPart1[i], this.distancesPart1[i]);
        }
        return product;
    }

    @Override
    public long getPart2Solution() {
        long product = 1L;
        for (int i = 0; i < this.timesPart2.length; i++) {
            product *= numberOfSolutions(this.timesPart2[i], this.distancesPart2[i]);
        }
        return product;
    }

    private long numberOfSolutions(final long time, final long distance) {
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
