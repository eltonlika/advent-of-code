package aoc;

import static org.assertj.core.api.Assertions.assertThat;

import aoc.y2023.Day1;
import aoc.y2023.Day10;
import aoc.y2023.Day2;
import aoc.y2023.Day3;
import aoc.y2023.Day4;
import aoc.y2023.Day5;
import aoc.y2023.Day6;
import aoc.y2023.Day7;
import aoc.y2023.Day8;
import aoc.y2023.Day9;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TestSolutions2023 {

    @Test
    public void day1() {
        assertThat(new Day1().withInputFile("2023/1_example1.txt"))
                .returns(142L, AoC::getPart1Solution);

        assertThat(new Day1().withInputFile("2023/1_example2.txt"))
                .returns(281L, AoC::getPart2Solution);

        assertThat(new Day1().withInputFile("2023/1_input.txt"))
                .returns(55834L, AoC::getPart1Solution)
                .returns(53221L, AoC::getPart2Solution);
    }

    @Test
    public void day2() {
        assertThat(new Day2().withInputFile("2023/2_example1.txt"))
                .returns(8L, AoC::getPart1Solution)
                .returns(2286L, AoC::getPart2Solution);

        assertThat(new Day2().withInputFile("2023/2_input.txt"))
                .returns(2551L, AoC::getPart1Solution)
                .returns(62811L, AoC::getPart2Solution);
    }

    @Test
    public void day3() {
        assertThat(new Day3().withInputFile("2023/3_example1.txt"))
                .returns(4361L, AoC::getPart1Solution)
                .returns(467835L, AoC::getPart2Solution);

        assertThat(new Day3().withInputFile("2023/3_input.txt"))
                .returns(532331L, AoC::getPart1Solution)
                .returns(82301120L, AoC::getPart2Solution);
    }

    @Test
    public void day4() {
        assertThat(new Day4().withInputFile("2023/4_example1.txt"))
                .returns(13L, AoC::getPart1Solution)
                .returns(30L, AoC::getPart2Solution);

        assertThat(new Day4().withInputFile("2023/4_input.txt"))
                .returns(24733L, AoC::getPart1Solution)
                .returns(5422730L, AoC::getPart2Solution);
    }

    @Test
    public void day5() {
        assertThat(new Day5().withInputFile("2023/5_example1.txt"))
                .returns(35L, AoC::getPart1Solution)
                .returns(46L, AoC::getPart2Solution);

        assertThat(new Day5().withInputFile("2023/5_input.txt"))
                .returns(510109797L, AoC::getPart1Solution)
                .returns(9622622L, AoC::getPart2Solution);
    }

    @Test
    public void day6() {
        assertThat(new Day6().withInputFile("2023/6_example1.txt"))
                .returns(288L, AoC::getPart1Solution)
                .returns(71503L, AoC::getPart2Solution);

        assertThat(new Day6().withInputFile("2023/6_input.txt"))
                .returns(1195150L, AoC::getPart1Solution)
                .returns(42550411L, AoC::getPart2Solution);
    }

    @Test
    public void day7() {
        assertThat(new Day7().withInputFile("2023/7_example1.txt"))
                .returns(6440L, AoC::getPart1Solution)
                .returns(5905L, AoC::getPart2Solution);

        assertThat(new Day7().withInputFile("2023/7_input.txt"))
                .returns(251029473L, AoC::getPart1Solution)
                .returns(251003917L, AoC::getPart2Solution);
    }

    @Test
    public void day8() {
        assertThat(new Day8().withInputFile("2023/8_example1.txt"))
                .returns(2L, AoC::getPart1Solution);

        assertThat(new Day8().withInputFile("2023/8_example2.txt"))
                .returns(6L, AoC::getPart1Solution);

        assertThat(new Day8().withInputFile("2023/8_example3.txt"))
                .returns(6L, AoC::getPart2Solution);

        assertThat(new Day8().withInputFile("2023/8_input.txt"))
                .returns(15989L, AoC::getPart1Solution)
                .returns(13830919117339L, AoC::getPart2Solution);
    }

    @Test
    public void day9() {
        assertThat(new Day9().withInputFile("2023/9_example1.txt"))
                .returns(114L, AoC::getPart1Solution)
                .returns(2L, AoC::getPart2Solution);

        assertThat(new Day9().withInputFile("2023/9_input.txt"))
                .returns(1882395907L, AoC::getPart1Solution)
                .returns(1005L, AoC::getPart2Solution);
    }

    @Nested
    class TestDay10 {

        @Test
        public void test_example1() {
            assertThat(new Day10().withInputFile("2023/10_example1.txt"))
                    .returns(8L, AoC::getPart1Solution);
        }

        @Test
        public void test_example2() {
            assertThat(new Day10().withInputFile("2023/10_example2.txt"))
                    .returns(8L, AoC::getPart2Solution);
        }

        @Test
        public void test_example3() {
            assertThat(new Day10().withInputFile("2023/10_example3.txt"))
                    .returns(10L, AoC::getPart2Solution);
        }

        @Test
        public void test_input() {
            assertThat(new Day10().withInputFile("2023/10_input.txt"))
                    .returns(6870L, AoC::getPart1Solution)
                    .returns(287L, AoC::getPart2Solution);
        }
    }
}
