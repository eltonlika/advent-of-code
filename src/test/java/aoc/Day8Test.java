package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day8Test {

    @Test
    public void part1_example1() {
        assertThat(Day8.solvePart1(read("8/example1.txt"))).isEqualTo(2);
    }

    @Test
    public void part1_example2() {
        assertThat(Day8.solvePart1(read("8/example2.txt"))).isEqualTo(6);
    }

    @Test
    public void part1_input() {
        assertThat(Day8.solvePart1(read("8/input.txt"))).isEqualTo(15989);
    }

    @Test
    public void part2_example3() {
        assertThat(Day8.solvePart2(read("8/example3.txt"))).isEqualTo(6);
    }

    @Test
    public void part2_input() {
        assertThat(Day8.solvePart2(read("8/input.txt"))).isEqualTo(13830919117339L);
    }
}
