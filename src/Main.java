public class Main {
    public static void main(String[] args) {
        Agent agent = new UniformCostSearchAgent();
        Level level = new Level("levels/level01/level01.txt");

        // Level solutionLevel = agent.getSolutionLevel(level);
        // System.out.println(solutionLevel == null); // çözüm var mı yok mu false => var, true => yok

        LevelImageGenerator.createAndSaveLevelImage("deneme.png", level);
    }
}