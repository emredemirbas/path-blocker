import java.util.*;

public class UniformCostSearchAgent extends Agent {
    private Level bestSolution;
    private int lowestMovementAmount;

    public UniformCostSearchAgent() {
        bestSolution = null;
        lowestMovementAmount = Integer.MAX_VALUE; // büyük
    }

    // TODO
    @Override
    public Level getSolutionLevel(Level initialLevel) throws InterruptedException {
        PriorityQueue<Level> fringe = new PriorityQueue<>();

        fringe.add(initialLevel);
        int count = 0;

        while (!fringe.isEmpty()) {
            Level level = fringe.poll();
            // LevelVisualizer.displayLevelImage(level);
            // Thread.sleep(5 * 1000);

            if (level.isAgentAtGoalPosition() && lowestMovementAmount > level.getMovementAmount()) {
                lowestMovementAmount = level.getMovementAmount();
                bestSolution = level;
            }

            for (Direction direction : level.getMovableDirections()) {
                Level childLevel = new Level(level, direction);
                fringe.add(childLevel);
            }
        }

        return bestSolution;
    }
}
