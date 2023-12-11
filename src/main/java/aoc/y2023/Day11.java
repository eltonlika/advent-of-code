package aoc.y2023;

import static java.lang.Math.max;
import static java.lang.Math.min;

import aoc.AoC;
import aoc.util.Position2D;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 implements AoC {

    private Set<Integer> expandedX;
    private Set<Integer> expandedY;
    private Position2D[] galaxies;

    @Override
    public void load(String input) {
        final char[][] grid = input.lines().map(String::toCharArray).toArray(char[][]::new);
        this.expandedX = IntStream.range(0, grid[0].length).boxed().collect(Collectors.toSet());
        this.expandedY = IntStream.range(0, grid.length).boxed().collect(Collectors.toSet());
        this.galaxies =
                IntStream.range(0, grid.length)
                        .boxed()
                        .flatMap(
                                y ->
                                        IntStream.range(0, grid[y].length)
                                                .filter(x -> grid[y][x] == '#')
                                                .mapToObj(x -> new Position2D(x, y)))
                        .peek(pos -> this.expandedX.remove(pos.x()))
                        .peek(pos -> this.expandedY.remove(pos.y()))
                        .toArray(Position2D[]::new);
    }

    @Override
    public long getPart1Solution() {
        return getPairsDistanceSum(2);
    }

    @Override
    public long getPart2Solution() {
        return getPairsDistanceSum(1000000);
    }

    public long getPart2Solution(final long emptySpaceMultiplier) {
        return getPairsDistanceSum(emptySpaceMultiplier);
    }

    private long getPairsDistanceSum(final long emptySpaceMultiplier) {
        long sum = 0;
        for (int i = 0; i < galaxies.length; i++) {
            for (int j = i + 1; j < galaxies.length; j++) {
                sum += getShortestPath(galaxies[i], galaxies[j], emptySpaceMultiplier);
            }
        }
        return sum;
    }

    private long getShortestPath(
            final Position2D p1, final Position2D p2, final long emptySpaceMultiplier) {
        final int x1 = min(p1.x(), p2.x());
        final int x2 = max(p1.x(), p2.x());
        final int y1 = min(p1.y(), p2.y());
        final int y2 = max(p1.y(), p2.y());
        final long countExpandedX = expandedX.stream().filter(x -> x >= x1 && x <= x2).count();
        final long countExpandedY = expandedY.stream().filter(y -> y >= y1 && y <= y2).count();
        return (x2 - x1 - countExpandedX + emptySpaceMultiplier * countExpandedX)
                + (y2 - y1 - countExpandedY + emptySpaceMultiplier * countExpandedY);
    }
}
