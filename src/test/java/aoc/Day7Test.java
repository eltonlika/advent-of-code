package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day7Test {

    @Test
    public void part1_example() {
        assertThat(Day7.solvePart1(read("7/example.txt"))).isEqualTo(6440);
    }

    @Test
    public void part1_input() {
        assertThat(Day7.solvePart1(read("7/input.txt"))).isEqualTo(251029473);
    }

    @Test
    public void part2_example() {
        assertThat(Day7.solvePart2(read("7/example.txt"))).isEqualTo(5905);
    }

    @Test
    public void part2_input() {
        assertThat(Day7.solvePart2(read("7/input.txt"))).isEqualTo(251003917);
    }
}
