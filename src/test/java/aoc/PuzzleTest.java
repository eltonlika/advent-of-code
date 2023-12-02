package aoc;

import static aoc.util.Util.read;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PuzzleTest {

    @Test
    public void day1() {
        assertThat(Day1.solvePart1(read("1/example1.txt"))).isEqualTo(142);
        assertThat(Day1.solvePart1(read("1/input.txt"))).isEqualTo(55834);

        assertThat(Day1.solvePart2(read("1/example2.txt"))).isEqualTo(281);
        assertThat(Day1.solvePart2(read("1/input.txt"))).isEqualTo(53221);
    }

    @Test
    public void day2() {
        assertThat(Day2.solvePart1(read("2/example1.txt"))).isEqualTo(8);

        assertThat(Day2.solvePart2(read("2/example1.txt"))).isEqualTo(2286);
        assertThat(Day2.solvePart2(read("2/input.txt"))).isEqualTo(62811);
    }
}
