package aoc.y2023;

import aoc.AoC;
import aoc.util.Direction;
import aoc.util.Position2D;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Day16 implements AoC {

    private char[][] layout;

    @Override
    public void load(String input) {
        this.layout = input.lines().map(String::toCharArray).toArray(char[][]::new);
    }

    @Override
    public long getPart1Solution() {
        final BitSet visited = new BitSet(layout.length * layout[0].length);
        visit(new Position2D(0, 0), Direction.right, visited);
        return visited.cardinality();
    }

    @Override
    public long getPart2Solution() {
        long max = 0L;

        final BitSet visited = new BitSet(layout.length * layout[0].length);

        for (int y = 0; y < layout.length; y++) {
            visit(new Position2D(0, y), Direction.right, visited);
            max = Math.max(max, visited.cardinality());
            visited.clear();

            visit(new Position2D(layout[y].length - 1, y), Direction.left, visited);
            max = Math.max(max, visited.cardinality());
            visited.clear();
        }

        for (int x = 0; x < layout[0].length; x++) {
            visit(new Position2D(x, 0), Direction.down, visited);
            max = Math.max(max, visited.cardinality());
            visited.clear();

            visit(new Position2D(x, layout.length - 1), Direction.up, visited);
            max = Math.max(max, visited.cardinality());
            visited.clear();
        }

        return max;
    }

    private void visit(
            final Position2D startingPosition,
            final Direction startingDirection,
            final BitSet visited) {
        final List<Beam> beams = new ArrayList<>();
        beams.add(new Beam(startingPosition, startingDirection));

        int branchIndex = 0;
        while (branchIndex < beams.size()) {
            final Beam beam = beams.get(branchIndex++);
            Position2D position = beam.position();
            Direction direction = beam.direction();

            loop1:
            while (position.inside(layout[0].length, layout.length)) {
                visited.set(position.y() * layout[0].length + position.x());

                if (layout[position.y()][position.x()] == '.') {
                    position = position.move(direction);

                } else if (layout[position.y()][position.x()] == '-') {
                    switch (direction) {
                        case up, down -> {
                            if (beams.contains(
                                    new Beam(position.move(Direction.left), Direction.left))) {
                                break loop1;
                            }
                            beams.add(new Beam(position.move(Direction.left), Direction.left));
                            position = position.move(Direction.right);
                            direction = Direction.right;
                        }
                        case left, right -> position = position.move(direction);
                    }

                } else if (layout[position.y()][position.x()] == '|') {
                    switch (direction) {
                        case left, right -> {
                            if (beams.contains(
                                    new Beam(position.move(Direction.up), Direction.up))) {
                                break loop1;
                            }
                            beams.add(new Beam(position.move(Direction.up), Direction.up));
                            position = position.move(Direction.down);
                            direction = Direction.down;
                        }
                        case up, down -> position = position.move(direction);
                    }

                } else if (layout[position.y()][position.x()] == '/') {
                    switch (direction) {
                        case up -> {
                            position = position.move(Direction.right);
                            direction = Direction.right;
                        }
                        case down -> {
                            position = position.move(Direction.left);
                            direction = Direction.left;
                        }
                        case left -> {
                            position = position.move(Direction.down);
                            direction = Direction.down;
                        }
                        case right -> {
                            position = position.move(Direction.up);
                            direction = Direction.up;
                        }
                    }

                } else if (layout[position.y()][position.x()] == '\\') {
                    switch (direction) {
                        case up -> {
                            position = position.move(Direction.left);
                            direction = Direction.left;
                        }
                        case down -> {
                            position = position.move(Direction.right);
                            direction = Direction.right;
                        }
                        case left -> {
                            position = position.move(Direction.up);
                            direction = Direction.up;
                        }
                        case right -> {
                            position = position.move(Direction.down);
                            direction = Direction.down;
                        }
                    }
                }
            }
        }
    }

    record Beam(Position2D position, Direction direction) {}
}
