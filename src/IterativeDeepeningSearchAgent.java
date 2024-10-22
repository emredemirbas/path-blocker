import java.util.*;

public class IterativeDeepeningSearchAgent extends Agent {

    @Override
    Level getSolutionLevel(Level initialLevel) {
        int depth = 0;

        // Keep increasing the depth limit until a solution is found
        while (true) {
            Level result = depthLimitedSearch(initialLevel, depth);
            if (result != null) {
                return result; // Solution found
            }
            depth++;
        }
    }

    // Perform a depth-limited DFS
    private Level depthLimitedSearch(Level level, int limit) {
        Stack<Level> stack = new Stack<>();
        Map<Level, Integer> depthMap = new HashMap<>();

        stack.push(level);
        depthMap.put(level, 0);

        while (!stack.isEmpty()) {
            Level currentLevel = stack.pop();
            int currentDepth = depthMap.get(currentLevel);

            if (currentLevel.isAgentAtGoalPosition()) {
                return currentLevel;
            }

            // If the current depth is less than the limit, explore further
            if (currentDepth < limit) {
                for (Direction direction : currentLevel.getMovableDirections()) {
                    Level childLevel = new Level(currentLevel, direction);
                    stack.push(childLevel);
                    depthMap.put(childLevel, currentDepth + 1);
                }
            }
        }

        return null; // No solution found within the current depth limit
    }
}
