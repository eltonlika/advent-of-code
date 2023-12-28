package aoc.util;

public enum Direction {
    up('^'),
    down('v'),
    left('<'),
    right('>');

    private final char symbol;

    Direction(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }

    public Direction rotate90() {
        return switch (this) {
            case up -> right;
            case down -> left;
            case left -> up;
            case right -> down;
        };
    }

    public Direction rotate90Reverse() {
        return switch (this) {
            case up -> left;
            case down -> right;
            case left -> down;
            case right -> up;
        };
    }
}
