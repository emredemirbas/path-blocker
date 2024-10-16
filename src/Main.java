import java.awt.image.AreaAveragingScaleFilter;
import java.util.Arrays;

// TODO: txt'deki formatlarda bir problem var. (Muhtemelen Level'da [x][y] yerine [y][x] göndermemiz lazım.)

// TODO 2: txt file'ları tek bir folder içinde (ör. txtfiles); output png'leri ise farklı bir folder içinde (outputs) tutmak daha iyi olabilir.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        //Agent agent = new UniformCostSearchAgent();
        //Agent agent = new BreathFirstSearchAgent();
        Agent agent = new DepthFirstSearchAgent();
        //Agent agent = new DepthFirstSearchWithRecursionAgent();
        Level level = new Level("levels/level02/level02.txt");

        Level solutionLevel = agent.getSolutionLevel(level);
        System.out.println(solutionLevel == null);

    }
}