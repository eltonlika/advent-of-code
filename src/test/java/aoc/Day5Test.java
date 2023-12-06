package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Day5Test {

    @Test
    public void part1_example() {
        assertThat(new Day5().solvePart1(read("5/example1.txt"))).isEqualTo(35);
    }

    @Test
    public void part1_input() {
        assertThat(new Day5().solvePart1(read("5/input.txt"))).isEqualTo(510109797);
    }

    @Test
    public void part2_example() {
        assertThat(new Day5().solvePart2(read("5/example1.txt"))).isEqualTo(46);
    }

    @Disabled("very slow, runs up to 7 minutes")
    @Test
    public void part2_input() {
        assertThat(new Day5().solvePart2(read("5/input.txt"))).isEqualTo(9622622);
    }

    @Test
    public void part2_optimized_example() {
        assertThat(new Day5().solvePart2Optimized(read("5/example1.txt"))).isEqualTo(46);
    }

    @Test
    public void part2_optimized_input() {
        assertThat(new Day5().solvePart2Optimized(read("5/input.txt"))).isEqualTo(9622622);
    }
}
