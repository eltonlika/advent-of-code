package aoc.y2023;

import static java.util.function.Predicate.not;

import aoc.AoC;
import aoc.util.Connection2D;
import aoc.util.Direction;
import aoc.util.Position2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day10 implements AoC {

    private char[][] tiles;
    private Position2D start;

    @Override
    public void load(final String input) {
        this.tiles = input.lines().map(String::toCharArray).toArray(char[][]::new);

        // find starting point
        this.start = Objects.requireNonNull(getStartPosition());

        // update starting point char with correct pipe symbol
        final Direction[] startPipeDirections = getConnections(start);
        final Connection2D startPipe = Connection2D.get(startPipeDirections);
        this.tiles[start.y()][start.x()] = startPipe.getSymbol();
    }

    @Override
    public long getPart1Solution() {
        return getLoop().size() / 2;
    }

    @Override
    public long getPart2Solution() {
        final boolean[][] isPartOfLoop = new boolean[tiles.length][tiles[0].length];
        getLoop().forEach(pos -> isPartOfLoop[pos.y()][pos.x()] = true);

        long sum = 0;
        for (int y = 0; y < tiles.length; y++) {
            long countNorth = 0;
            for (int x = 0; x < tiles[y].length; x++) {
                if (isPartOfLoop[y][x]) {
                    switch (tiles[y][x]) {
                        case '|', 'J', 'L' -> countNorth++;
                    }
                    continue;
                }
                if (countNorth % 2 == 1) {
                    sum++;
                }
            }
        }
        return sum;
    }

    private Position2D getStartPosition() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                if (tiles[y][x] == 'S') {
                    return new Position2D(x, y);
                }
            }
        }
        return null;
    }

    private Direction[] getConnections(final Position2D pos) {
        final Direction[] directions = new Direction[2];
        int i = 0;
        final Position2D north = pos.up();
        if (north.inside(tiles[0].length, tiles.length)) {
            switch (north.get(tiles)) {
                case '|', '7', 'F' -> directions[i++] = Direction.up;
            }
        }
        final Position2D south = pos.down();
        if (south.inside(tiles[0].length, tiles.length)) {
            switch (south.get(tiles)) {
                case '|', 'L', 'J' -> directions[i++] = Direction.down;
            }
        }
        final Position2D west = pos.left();
        if (west.inside(tiles[0].length, tiles.length)) {
            switch (west.get(tiles)) {
                case '-', 'L', 'F' -> directions[i++] = Direction.left;
            }
        }
        final Position2D east = pos.right();
        if (east.inside(tiles[0].length, tiles.length)) {
            switch (east.get(tiles)) {
                case '-', 'J', '7' -> directions[i] = Direction.right;
            }
        }
        return directions;
    }

    private List<Position2D> getLoop() {
        final List<Position2D> result = new ArrayList<>();
        Position2D currentPosition = start;
        Direction[] currentDirections = Connection2D.get(start.get(tiles)).getDirections();
        Direction cameFrom = currentDirections[0];
        do {
            result.add(currentPosition);
            final Direction newDirection =
                    Arrays.stream(currentDirections)
                            .filter(not(cameFrom::equals))
                            .findFirst()
                            .orElseThrow();
            currentPosition = currentPosition.move(newDirection);
            currentDirections = Connection2D.get(currentPosition.get(tiles)).getDirections();
            cameFrom = newDirection.opposite();
        } while (!currentPosition.equals(start));
        return result;
    }
}
