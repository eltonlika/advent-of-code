package aoc.y2023;

import aoc.AoC;
import aoc.util.Connection2D;
import aoc.util.Direction;
import aoc.util.Position2D;

import java.util.Arrays;
import java.util.List;

public class Day18 implements AoC {

    private char[][] digMap;

    @Override
    public void load(final String input) {
        final List<Instruction> digPlan =
                input.lines()
                        .map(s -> s.split("\\s+"))
                        .map(
                                ss ->
                                        new Instruction(
                                                switch (ss[0]) {
                                                    case "U" -> Direction.up;
                                                    case "D" -> Direction.down;
                                                    case "L" -> Direction.left;
                                                    case "R" -> Direction.right;
                                                    default ->
                                                            throw new IllegalStateException(
                                                                    "Unexpected value: " + ss[0]);
                                                },
                                                Integer.parseInt(ss[1])))
                        .toList();

        int maxLeft = 0, maxRight = 0, maxUp = 0, maxDown = 0;
        int currentLeft = 0, currentRight = 0, currentUp = 0, currentDown = 0;
        for (final Instruction instruction : digPlan) {
            switch (instruction.direction()) {
                case left -> {
                    currentLeft += instruction.steps();
                    currentRight -= instruction.steps();
                }
                case right -> {
                    currentLeft -= instruction.steps();
                    currentRight += instruction.steps();
                }
                case up -> {
                    currentUp += instruction.steps();
                    currentDown -= instruction.steps();
                }
                case down -> {
                    currentUp -= instruction.steps();
                    currentDown += instruction.steps();
                }
            }
            if (maxLeft < currentLeft) {
                maxLeft = currentLeft;
            }
            if (maxRight < currentRight) {
                maxRight = currentRight;
            }
            if (maxUp < currentUp) {
                maxUp = currentUp;
            }
            if (maxDown < currentDown) {
                maxDown = currentDown;
            }
        }

        this.digMap = new char[maxUp + maxDown + 1][maxLeft + maxRight + 1];
        Arrays.stream(this.digMap).forEach(l -> Arrays.fill(l, '.'));

        Position2D current = new Position2D(maxLeft, maxUp);

        for (int i = 0; i < digPlan.size(); i++) {
            final Instruction currentInstruction = digPlan.get(i);
            final Instruction nextInstruction = digPlan.get(i == digPlan.size() - 1 ? 0 : i + 1);

            for (int step = 0; step < currentInstruction.steps(); step++) {
                final Direction nextDirection =
                        step == currentInstruction.steps() - 1
                                ? nextInstruction.direction()
                                : currentInstruction.direction();

                final Connection2D connection =
                        Connection2D.get(
                                new Direction[] {
                                    currentInstruction.direction().opposite(), nextDirection
                                });

                current = current.move(currentInstruction.direction());
                current.set(this.digMap, connection.getSymbol());
            }
        }
    }

    @Override
    public long getPart1Solution() {
        return cubicMeters();
    }

    @Override
    public long getPart2Solution() {
        throw new UnsupportedOperationException();
    }

    private long cubicMeters() {
        long count = 0;
        for (final char[] line : this.digMap) {
            long countForLine = 0;
            long countUp = 0;
            for (char c : line) {
                switch (c) {
                    case '|', 'J', 'L' -> countUp++;
                }
                if (c != '.' || countUp % 2 == 1) {
                    countForLine++;
                }
            }
            count += countForLine;
            System.out.println(new String(line) + "     // " + countForLine);
        }
        return count;
    }

    private record Instruction(Direction direction, int steps) {}
}
