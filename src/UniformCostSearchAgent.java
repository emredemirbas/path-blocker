import java.util.*;

public class UniformCostSearchAgent extends Agent {
    private Level bestSolution;
    private int lowestMovementAmount;

    public UniformCostSearchAgent() {
        bestSolution = null;
        lowestMovementAmount = Integer.MAX_VALUE;
    }

    @Override
    public Level getSolutionLevel(Level initialLevel) {
        PriorityQueue<Level> fringe = new PriorityQueue<>();

        fringe.add(initialLevel);

        while (!fringe.isEmpty()) {
            Level level = fringe.poll();

            if (level.isAgentAtGoalPosition() && lowestMovementAmount > level.getMovementAmount()) {
                lowestMovementAmount = level.getMovementAmount();
                bestSolution = level;
            }

            for (Direction direction : level.getMovableDirections()) {
                Level childLevel = new Level(level, direction);
                if(bestSolution != null){
                    if(level.getMovementAmount() > lowestMovementAmount){
                        continue;
                    }
                }
                fringe.add(childLevel);
            }
        }
        return bestSolution;
    }
}
