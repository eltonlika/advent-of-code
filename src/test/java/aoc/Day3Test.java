package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day3Test {

    @Test
    public void part1_example() {
        assertThat(Day3.solvePart1(read("3/example1.txt"))).isEqualTo(4361);
    }

    @Test
    public void part1_input() {
        assertThat(Day3.solvePart1(read("3/input.txt"))).isEqualTo(532331);
    }

    @Test
    public void part2_example() {
        assertThat(Day3.solvePart2(read("3/example1.txt"))).isEqualTo(467835);
    }

    @Test
    public void part2_input() {
        assertThat(Day3.solvePart2(read("3/input.txt"))).isEqualTo(82301120);
    }
}
