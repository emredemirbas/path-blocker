import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearchAgent extends Agent{

    @Override
    public Level getSolutionLevel(Level initialLevel) {
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