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


    public Level(int width, int height, int[] goalPosition, int[] startPosition, int[][] wallPositions) {
        this.world = new boolean[width][height];
        this.width = width;
        this.height = height;
        this.goalPosition = goalPosition;
        this.startPosition = startPosition;
        this.agentPositions = new ArrayList<>();
        this.agentPositions.add(startPosition);

        for (int[] wallPosition : wallPositions) {
            this.world[wallPosition[0]][wallPosition[1]] = true;
        }

    }

    public Level(String path) {
        this.agentPositions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
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
    }

    public Level(Level level) {
        this.world = level.world;
        this.agentPositions = new ArrayList<>(level.agentPositions);
        this.width = level.width;
        this.height = level.height;
        this.goalPosition = level.goalPosition;
        this.startPosition = level.startPosition;
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

    public boolean[][] getWorld() {
        return world;
    }

    private int getAgentX() {
        return this.agentPositions.get(this.agentPositions.size() - 1)[0];
    }

    private int getAgentY() {
        return this.agentPositions.get(this.agentPositions.size() - 1)[1];
    }

    private int[] getAgentXY() {
        return new int[]{this.getAgentX(), this.getAgentY()};
    }

    private int[] getNextAgentXY(Direction direction) {
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
        return new int[]{x, y};
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

    private void putWall(int x, int y) {
        if (x < this.width && y < this.height && x >= 0 && y >= 0) {
            this.world[x][y] = true;
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
        this.putWall(x, y);
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
        int[] nextPositions = this.getNextAgentXY(direction);
        int x = nextPositions[0];
        int y = nextPositions[1];
        return this.isValidCoordinate(x, y) && !this.hasWallAt(x, y);
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    private boolean hasWallAt(int x, int y) {
        return this.isValidCoordinate(x, y) && this.world[x][y];
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
            this.putWall(direction);
            this.agentPositions.add(this.getNextAgentXY(direction));
        }
    }

    public int getMovementAmount() {
        return this.agentPositions.size();
    }

    public boolean isAgentAtGoalPosition() {
        return this.getAgentX() == this.goalPosition[0] && this.getAgentY() == this.goalPosition[1];
    }


    @Override
    public int compareTo(Level other) {
        return Integer.compare(this.agentPositions.size(), other.agentPositions.size());
    }
}
