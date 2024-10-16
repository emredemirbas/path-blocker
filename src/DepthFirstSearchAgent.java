import java.util.Stack;

public class DepthFirstSearchAgent extends Agent{

    @Override
    Level getSolutionLevel(Level initialLevel) throws InterruptedException {
        Stack<Level> fringe = new Stack<>();
        fringe.push(initialLevel);

        while(!fringe.isEmpty()){
            Level level = fringe.pop();
            if(level.isAgentAtGoalPosition()){
                return level;
            }else{
                for(Direction movableDirection:level.getMovableDirections()){
                    Level childLevel = new Level(level, movableDirection);
                    fringe.push(childLevel);
                }
            }
        }

        return null;
    }

}
