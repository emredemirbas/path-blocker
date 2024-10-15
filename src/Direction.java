public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Direction getNegatedDirection(Direction d) {
        return switch (d) {
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP -> DOWN;
            case DOWN -> UP;
        };
    }
}
