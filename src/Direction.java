public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Direction getNegatedDirection(Direction direction) {
        return switch (direction) {
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP -> DOWN;
            case DOWN -> UP;
        };
    }
}
