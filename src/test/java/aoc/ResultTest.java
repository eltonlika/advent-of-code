package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ResultTest {

    @Test
    public void day1() {
        assertThat(Day1.solve(read("1/part1.txt"))).isEqualTo(142);
        assertThat(Day1.solve(read("1/part2.txt"))).isEqualTo(281);
        assertThat(Day1.solve(read("1/input.txt"))).isEqualTo(53221);
    }
}
