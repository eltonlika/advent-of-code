package aoc.util;

public enum Direction {
    up,
    down,
    left,
    right;

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

    public Direction opposite() {
        return this.rotate90().rotate90();
    }
}
