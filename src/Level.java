import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Level {
    private boolean[][] world;

    private List<int[]> agentPositions;
    private Agent agent;

    private int width;

    private int height;

    private int[] goalPosition;

    private int[] startPosition;

    public Level(int width, int height, int[] goalPosition, int[] startPosition, int[][] wallPositions, Agent agent){
        this.world = new boolean[width][height];
        this.agent = agent;
        this.width = width;
        this.height = height;
        this.goalPosition = goalPosition;
        this.startPosition = startPosition;
        this.agentPositions.add(startPosition);

        for(int[] wallPosition:wallPositions){
            this.world[wallPosition[0]][wallPosition[1]] = true;
        }

    }

    public Level(String path){
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("s")){
                    String[] splittedLine = line.substring(1).trim().split(",");
                    this.startPosition = new int[]{Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1])};
                    this.agentPositions.add(startPosition);
                } else if (line.startsWith("g")) {
                    String[] splittedLine = line.substring(1).trim().split(",");
                    this.goalPosition = new int[]{Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1])};
                } else if(line.startsWith("w")){
                    this.width = Integer.parseInt(line.substring(1));
                }else if(line.startsWith("h")){
                    this.height = Integer.parseInt(line.substring(1));
                    this.world = new boolean[this.width][this.height];
                }else{
                    String[] splittedLine = line.trim().split(",");
                    int[] wallPosition = {Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1])};
                    this.world[wallPosition[0]][wallPosition[1]] = true;
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    private void putWall(int x, int y){
        if(x<this.width && y<this.height && x>=0 && y>=0){
            this.world[x][y] = true;
        }
    }

//    private boolean isMovable(int x, int y){
//
//    }

    private boolean isCoordinateInsideWorld(int x, int y){
        return x>=0 && x<this.width && y>=0 && y<this.height;
    }

    private boolean isWallAtCoordinate(int x, int y){
        return this.world[x][y];
    }
    public List<Directions> getMovableDirections(){
        List<Directions> movableDirections = new ArrayList<>();
        int[] currentAgentPosition = this.agentPositions.get(agentPositions.size()-1);
        int agentX = currentAgentPosition[0];
        int agentY = currentAgentPosition[1];
        int agentLeft = currentAgentPosition[0] - 1;
        int agentRight = currentAgentPosition[0] + 1;
        int agentUp = currentAgentPosition[1] + 1;
        int agentDown = currentAgentPosition[1] - 1;

        //Check left
        if(isCoordinateInsideWorld(agentLeft, agentY) && !isWallAtCoordinate(agentLeft, agentY)){
            movableDirections.add(Directions.LEFT);
        }
        //Check right
        if(isCoordinateInsideWorld(agentRight, agentY) && !isWallAtCoordinate(agentRight, agentY)){
            movableDirections.add(Directions.RIGHT);
        }

        if(isCoordinateInsideWorld(agentX, agentUp) && !isWallAtCoordinate(agentX, agentUp)){
            movableDirections.add(Directions.UP);
        }

        if(isCoordinateInsideWorld(agentX, agentDown) && !isWallAtCoordinate(agentX, agentDown)){
            movableDirections.add(Directions.DOWN);
        }

        return movableDirections;

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

}
