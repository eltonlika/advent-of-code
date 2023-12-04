package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day4Test {

    @Test
    public void part1_example() {
        assertThat(Day4.solvePart1(read("4/example1.txt"))).isEqualTo(13);
    }

    @Test
    public void part1_input() {
        assertThat(Day4.solvePart1(read("4/input.txt"))).isEqualTo(24733);
    }

    @Test
    public void part2_example() {
        assertThat(Day4.solvePart2(read("4/example1.txt"))).isEqualTo(30);
    }

    @Test
    public void part2_input() {
        assertThat(Day4.solvePart2(read("4/input.txt"))).isEqualTo(5422730);
    }
}
