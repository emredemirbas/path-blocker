import java.awt.image.AreaAveragingScaleFilter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Agent agent = new UniformCostSearchAgent();
        Level level = new Level("levels/level01/level01.txt");

        Level solutionLevel = agent.getSolutionLevel(level);
        System.out.println(solutionLevel == null);

        LevelImageGenerator.createAndSaveLevelImage("deneme.png", solutionLevel);
    }
}