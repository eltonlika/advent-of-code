package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day6Test {

    @Test
    public void part1_example() {
        assertThat(Day6.solvePart1(read("6/example1.txt"))).isEqualTo(288);
    }

    @Test
    public void part1_input() {
        assertThat(Day6.solvePart1(read("6/input.txt"))).isEqualTo(1195150);
    }

    @Test
    public void part2_example() {
        assertThat(Day6.solvePart2(read("6/example1.txt"))).isEqualTo(71503);
    }

    @Test
    public void part2_input() {
        assertThat(Day6.solvePart2(read("6/input.txt"))).isEqualTo(42550411);
    }
}
