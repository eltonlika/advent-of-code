package aoc.util;

import java.util.Arrays;
import java.util.Set;

public enum Connection2D {
    upDown('|', new Direction[] {Direction.up, Direction.down}),
    leftRight('-', new Direction[] {Direction.left, Direction.right}),
    upRight('L', new Direction[] {Direction.up, Direction.right}),
    upLeft('J', new Direction[] {Direction.up, Direction.left}),
    downRight('F', new Direction[] {Direction.down, Direction.right}),
    downLeft('7', new Direction[] {Direction.down, Direction.left});

    private final char symbol;
    private final Direction[] directions;

    Connection2D(final char symbol, final Direction[] directions) {
        this.symbol = symbol;
        this.directions = directions;
    }

    public static Connection2D get(final char symbol) {
        return Arrays.stream(values()).filter(p -> p.symbol == symbol).findFirst().orElse(null);
    }

    public static Connection2D get(final Direction[] directions) {
        return Arrays.stream(values())
                .filter(p -> Set.of(directions).equals(Set.of(p.directions)))
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
