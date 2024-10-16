import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BreathFirstSearchAgent extends Agent{

    @Override
    public Level getSolutionLevel(Level initialLevel) throws InterruptedException {
        Queue<Level> fringe = new LinkedList<>();

        fringe.add(initialLevel);

        while (!fringe.isEmpty()) {
            Level level = fringe.poll();

            if (level.isAgentAtGoalPosition()) {
                return level;
            }

            for (Direction direction : level.getMovableDirections()) {
                Level childLevel = new Level(level, direction);
                fringe.add(childLevel);
            }
        }

        return null;
    }
}
