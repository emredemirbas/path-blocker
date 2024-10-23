import java.util.*;

public class UniformCostSearchAgent extends Agent {

    public UniformCostSearchAgent() {

    }

    @Override
    public Level getSolutionLevel(Level initialLevel) {
        PriorityQueue<Level> fringe = new PriorityQueue<>();
        Level bestSolution = null;
        int lowestMovementAmount = Integer.MAX_VALUE;

        fringe.add(initialLevel);

        while (!fringe.isEmpty()) {
            Level level = fringe.poll();

            if (level.isAgentAtGoalPosition() && lowestMovementAmount > level.getMovementAmount()) {
                lowestMovementAmount = level.getMovementAmount();
                bestSolution = level;
            }

            for (Direction direction : level.getMovableDirections()) {
                Level childLevel = new Level(level, direction);
                if (bestSolution != null && level.getMovementAmount() > lowestMovementAmount) {
                    continue;
                }
                fringe.add(childLevel);
            }
        }
        return bestSolution;
    }
}
