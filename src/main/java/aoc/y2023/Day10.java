package aoc.y2023;

import static java.util.function.Predicate.not;

import aoc.AoC;
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
        final Pipe startPipe = Pipe.getPipe(startPipeDirections);
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
                        case '|', 'J', 'L':
                            countNorth++;
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
                case '|', '7', 'F' -> directions[i++] = Direction.NORTH;
            }
        }
        final Position2D south = pos.down();
        if (south.inside(tiles[0].length, tiles.length)) {
            switch (south.get(tiles)) {
                case '|', 'L', 'J' -> directions[i++] = Direction.SOUTH;
            }
        }
        final Position2D west = pos.left();
        if (west.inside(tiles[0].length, tiles.length)) {
            switch (west.get(tiles)) {
                case '-', 'L', 'F' -> directions[i++] = Direction.WEST;
            }
        }
        final Position2D east = pos.right();
        if (east.inside(tiles[0].length, tiles.length)) {
            switch (east.get(tiles)) {
                case '-', 'J', '7' -> directions[i] = Direction.EAST;
            }
        }
        return directions;
    }

    private List<Position2D> getLoop() {
        final List<Position2D> result = new ArrayList<>();
        Position2D currentPosition = start;
        Direction[] currentDirections = Pipe.getPipe(start.get(tiles)).getDirections();
        Direction cameFrom = currentDirections[0];
        do {
            result.add(currentPosition);
            final Direction newDirection =
                    Arrays.stream(currentDirections)
                            .filter(not(cameFrom::equals))
                            .findFirst()
                            .orElseThrow();
            currentPosition =
                    switch (newDirection) {
                        case NORTH -> currentPosition.up();
                        case SOUTH -> currentPosition.down();
                        case WEST -> currentPosition.left();
                        case EAST -> currentPosition.right();
                    };
            currentDirections = Pipe.getPipe(currentPosition.get(tiles)).getDirections();
            cameFrom = newDirection.opposite();
        } while (!currentPosition.equals(start));
        return result;
    }

    private enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST;

        public Direction opposite() {
            return switch (this) {
                case NORTH -> SOUTH;
                case SOUTH -> NORTH;
                case WEST -> EAST;
                case EAST -> WEST;
            };
        }
    }

    private enum Pipe {
        VERTICAL('|', new Direction[] {Direction.NORTH, Direction.SOUTH}),
        HORIZONTAL('-', new Direction[] {Direction.WEST, Direction.EAST}),
        NORTH_EAST('L', new Direction[] {Direction.NORTH, Direction.EAST}),
        NORTH_WEST('J', new Direction[] {Direction.NORTH, Direction.WEST}),
        SOUTH_EAST('F', new Direction[] {Direction.SOUTH, Direction.EAST}),
        SOUTH_WEST('7', new Direction[] {Direction.SOUTH, Direction.WEST});

        private final char symbol;
        private final Direction[] directions;

        Pipe(final char symbol, final Direction[] directions) {
            this.symbol = symbol;
            this.directions = directions;
        }

        public static Pipe getPipe(final char symbol) {
            return Arrays.stream(values()).filter(p -> p.symbol == symbol).findFirst().orElse(null);
        }

        public static Pipe getPipe(final Direction[] directions) {
            return Arrays.stream(values())
                    .filter(p -> Arrays.equals(p.directions, directions))
                    .findFirst()
                    .orElse(null);
        }

        public char getSymbol() {
            return symbol;
        }

        public Direction[] getDirections() {
            return directions;
        }
    }
}
