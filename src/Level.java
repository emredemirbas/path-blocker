import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Level implements Comparable<Level> {

    private static final Direction[] ALL_DIRECTIONS = {Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN};

    private boolean[][] world;

    private final List<int[]> agentPositions;

    private int width;

    private int height;

    private int[] goalPosition;

    private int[] startPosition;

    private Level parentLevel;

    public Level(String fileName) {
        this.agentPositions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("s")) {
                    String[] splittedLine = line.substring(1).trim().split(",");
                    this.startPosition = new int[]{Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1])};
                    this.agentPositions.add(startPosition);
                } else if (line.startsWith("g")) {
                    String[] splittedLine = line.substring(1).trim().split(",");
                    this.goalPosition = new int[]{Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1])};
                } else if (line.startsWith("w")) {
                    this.width = Integer.parseInt(line.substring(1));
                } else if (line.startsWith("h")) {
                    this.height = Integer.parseInt(line.substring(1));
                    this.world = new boolean[this.width][this.height];
                } else {
                    String[] splittedLine = line.trim().split(",");
                    int[] wallPosition = {Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1])};
                    this.world[wallPosition[0]][wallPosition[1]] = true;
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
        this.putWall(startPosition[0], startPosition[1]);
    }

    public Level(Level level) {
        this.world = Arrays.stream(level.getWorld()).map(boolean[]::clone).toArray(boolean[][]::new);
        this.agentPositions = new ArrayList<>(level.agentPositions);
        this.width = level.width;
        this.height = level.height;
        this.goalPosition = level.goalPosition;
        this.startPosition = level.startPosition;
        this.putWall(startPosition[0], startPosition[1]);
        this.parentLevel = level;
    }

    public Level(Level level, Direction direction) {
        this(level);
        this.moveAgent(direction);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getGoalPosition() {
        return goalPosition;
    }

    public int[] getStartPosition() {
        return startPosition;
    }

    public List<int[]> getAgentPositions() {
        return agentPositions;
    }

    public boolean[][] getWorld() {
        return world;
    }

    public Level getParentLevel() {
        return parentLevel;
    }

    public int getAgentX() {
        return this.agentPositions.get(this.agentPositions.size() - 1)[1]; //
    }

    public int getAgentY() {
        return this.agentPositions.get(this.agentPositions.size() - 1)[0]; //
    }

    private int[] getNextAgentYX(Direction direction) { //
        int x = this.getAgentX();
        int y = this.getAgentY();
        if (direction == Direction.LEFT) {
            x = this.getAgentLeft();
        } else if (direction == Direction.RIGHT) {
            x = this.getAgentRight();
        } else if (direction == Direction.UP) {
            y = this.getAgentUp();
        } else if (direction == Direction.DOWN) {
            y = this.getAgentDown();
        }
        return new int[]{y, x}; //
    }

    private int getAgentLeft() {
        return this.getAgentX() - 1;
    }

    private int getAgentRight() {
        return this.getAgentX() + 1;
    }

    private int getAgentUp() {
        return this.getAgentY() + 1;
    }

    private int getAgentDown() {
        return this.getAgentY() - 1;
    }


    private void putWall(int y, int x) { //
        if (x < this.width && y < this.height && x >= 0 && y >= 0) {
            this.world[y][x] = true;
        }
    }

    private void putWall(Direction direction) {
        int x = this.getAgentX();
        int y = this.getAgentY();
        switch (direction) {
            case LEFT -> x = this.getAgentLeft();
            case RIGHT -> x = this.getAgentRight();
            case UP -> y = this.getAgentUp();
            case DOWN -> y = this.getAgentDown();
        }
        this.putWall(y, x);
    }

    public boolean isMovable() {
        int countMovableDirections = 0;
        for (Direction d : Level.ALL_DIRECTIONS) {
            if (this.isMovable(d)) {
                countMovableDirections++;
            }
        }
        return countMovableDirections == 0;
    }

    private boolean isMovable(Direction direction) {
        int[] nextPositions = this.getNextAgentYX(direction);
        int x = nextPositions[1];
        int y = nextPositions[0];
        return this.isValidCoordinate(y, x) && !this.hasWallAt(y, x) && !this.isAgentAtGoalPosition();
    }

    private boolean isValidCoordinate(int y, int x) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    private boolean hasWallAt(int y, int x) {
        return this.isValidCoordinate(y, x) && this.world[y][x];
    }

    public List<Direction> getMovableDirections() {
        List<Direction> movableDirections = new ArrayList<>();
        for (Direction d : Level.ALL_DIRECTIONS) {
            if (this.isMovable(d)) {
                movableDirections.add(d);
            }
        }
        return movableDirections;
    }

    public void moveAgent(Direction direction) {
        while (this.isMovable(direction)) {
            // this.agentPositions.add(this.getNextAgentXY(direction));
            // this.putWall(Direction.getNegatedDirection(direction));
            // if (!this.isAgentAtGoalPosition()) {
            //     this.putWall(this.getAgentX(), this.getAgentY());
            // }
            this.agentPositions.add(this.getNextAgentYX(direction));
            this.putWall(Direction.getNegatedDirection(direction));
            if (this.isAgentAtGoalPosition()) {
                return;
            }
        }
    }


    public int getMovementAmount() {
        return this.agentPositions.size();
    }

    public boolean isAgentAtGoalPosition() {
        // System.out.printf("Agent:(%d,%d) | Goal:(%d,%d)\n", this.getAgentX(), this.getAgentY(), this.getGoalPosition()[0], this.getGoalPosition()[1]);
        return this.getAgentX() == this.goalPosition[1] && this.getAgentY() == this.goalPosition[0];
    }


    @Override
    public int compareTo(Level other) {
        return Integer.compare(this.agentPositions.size(), other.agentPositions.size());
    }
}
