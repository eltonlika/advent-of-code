package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day2Test {

    @Test
    public void part1_example() {
        assertThat(Day2.solvePart1(read("2/example1.txt"))).isEqualTo(8);
    }

    @Test
    public void part2_example() {
        assertThat(Day2.solvePart2(read("2/example1.txt"))).isEqualTo(2286);
    }

    @Test
    public void part2_input() {
        assertThat(Day2.solvePart2(read("2/input.txt"))).isEqualTo(62811);
    }
}
