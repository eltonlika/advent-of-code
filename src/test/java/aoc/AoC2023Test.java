package aoc;

import static org.assertj.core.api.Assertions.assertThat;

import aoc.y2023.Day1;
import aoc.y2023.Day10;
import aoc.y2023.Day11;
import aoc.y2023.Day12;
import aoc.y2023.Day13;
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

public class AoC2023Test {

    @Nested
    public class Day1Test {

        @Test
        public void example1() {
            assertThat(new Day1().withInputFile("2023/1_example1.txt"))
                    .returns(142L, AoC::getPart1Solution);
        }

        @Test
        public void example2() {
            assertThat(new Day1().withInputFile("2023/1_example2.txt"))
                    .returns(281L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day1().withInputFile("2023/1_input.txt"))
                    .returns(55834L, AoC::getPart1Solution)
                    .returns(53221L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day2Test {

        @Test
        public void example1() {
            assertThat(new Day2().withInputFile("2023/2_example1.txt"))
                    .returns(8L, AoC::getPart1Solution)
                    .returns(2286L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day2().withInputFile("2023/2_input.txt"))
                    .returns(2551L, AoC::getPart1Solution)
                    .returns(62811L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day3Test {

        @Test
        public void example1() {
            assertThat(new Day3().withInputFile("2023/3_example1.txt"))
                    .returns(4361L, AoC::getPart1Solution)
                    .returns(467835L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day3().withInputFile("2023/3_input.txt"))
                    .returns(532331L, AoC::getPart1Solution)
                    .returns(82301120L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day4Test {

        @Test
        public void example1() {
            assertThat(new Day4().withInputFile("2023/4_example1.txt"))
                    .returns(13L, AoC::getPart1Solution)
                    .returns(30L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day4().withInputFile("2023/4_input.txt"))
                    .returns(24733L, AoC::getPart1Solution)
                    .returns(5422730L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day5Test {

        @Test
        public void example1() {
            assertThat(new Day5().withInputFile("2023/5_example1.txt"))
                    .returns(35L, AoC::getPart1Solution)
                    .returns(46L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day5().withInputFile("2023/5_input.txt"))
                    .returns(510109797L, AoC::getPart1Solution)
                    .returns(9622622L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day6Test {

        @Test
        public void example1() {
            assertThat(new Day6().withInputFile("2023/6_example1.txt"))
                    .returns(288L, AoC::getPart1Solution)
                    .returns(71503L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day6().withInputFile("2023/6_input.txt"))
                    .returns(1195150L, AoC::getPart1Solution)
                    .returns(42550411L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day7Test {

        @Test
        public void example1() {
            assertThat(new Day7().withInputFile("2023/7_example1.txt"))
                    .returns(6440L, AoC::getPart1Solution)
                    .returns(5905L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day7().withInputFile("2023/7_input.txt"))
                    .returns(251029473L, AoC::getPart1Solution)
                    .returns(251003917L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day8Test {

        @Test
        public void example1() {
            assertThat(new Day8().withInputFile("2023/8_example1.txt"))
                    .returns(2L, AoC::getPart1Solution);
        }

        @Test
        public void example2() {
            assertThat(new Day8().withInputFile("2023/8_example2.txt"))
                    .returns(6L, AoC::getPart1Solution);
        }

        @Test
        public void example3() {
            assertThat(new Day8().withInputFile("2023/8_example3.txt"))
                    .returns(6L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day8().withInputFile("2023/8_input.txt"))
                    .returns(15989L, AoC::getPart1Solution)
                    .returns(13830919117339L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day9Test {

        @Test
        public void example1() {
            assertThat(new Day9().withInputFile("2023/9_example1.txt"))
                    .returns(114L, AoC::getPart1Solution)
                    .returns(2L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day9().withInputFile("2023/9_input.txt"))
                    .returns(1882395907L, AoC::getPart1Solution)
                    .returns(1005L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day10Test {

        @Test
        public void example1() {
            assertThat(new Day10().withInputFile("2023/10_example1.txt"))
                    .returns(8L, AoC::getPart1Solution);
        }

        @Test
        public void example2() {
            assertThat(new Day10().withInputFile("2023/10_example2.txt"))
                    .returns(8L, AoC::getPart2Solution);
        }

        @Test
        public void example3() {
            assertThat(new Day10().withInputFile("2023/10_example3.txt"))
                    .returns(10L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day10().withInputFile("2023/10_input.txt"))
                    .returns(6870L, AoC::getPart1Solution)
                    .returns(287L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day11Test {

        @Test
        public void example1() {
            assertThat(new Day11().withInputFile("2023/11_example1.txt"))
                    .returns(374L, AoC::getPart1Solution)
                    .returns(1030L, d -> ((Day11) d).getPart2Solution(10L))
                    .returns(8410L, d -> ((Day11) d).getPart2Solution(100L));
        }

        @Test
        public void input() {
            assertThat(new Day11().withInputFile("2023/11_input.txt"))
                    .returns(9647174L, AoC::getPart1Solution)
                    .returns(377318892554L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day12Test {

        @Test
        public void example1() {
            assertThat(new Day12().withInputFile("2023/12_example1.txt"))
                    .returns(21L, AoC::getPart1Solution)
                    .returns(525152L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day12().withInputFile("2023/12_input.txt"))
                    .returns(7670L, AoC::getPart1Solution)
                    .returns(157383940585037L, AoC::getPart2Solution);
        }
    }

    @Nested
    public class Day13Test {

        @Test
        public void example1() {
            assertThat(new Day13().withInputFile("2023/13_example1.txt"))
                    .returns(405L, AoC::getPart1Solution)
                    .returns(400L, AoC::getPart2Solution);
        }

        @Test
        public void input() {
            assertThat(new Day13().withInputFile("2023/13_input.txt"))
                    .returns(34993L, AoC::getPart1Solution)
                    .returns(29341L, AoC::getPart2Solution);
        }
    }
}
