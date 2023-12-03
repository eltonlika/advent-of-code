package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day1Test {

    @Test
    public void part1_example() {
        assertThat(Day1.solvePart1(read("1/example1.txt"))).isEqualTo(142);
    }

    @Test
    public void part1_input() {
        assertThat(Day1.solvePart1(read("1/input.txt"))).isEqualTo(55834);
    }

    @Test
    public void part2_example() {
        assertThat(Day1.solvePart2(read("1/example2.txt"))).isEqualTo(281);
    }

    @Test
    public void part2_input() {
        assertThat(Day1.solvePart2(read("1/input.txt"))).isEqualTo(53221);
    }
}
