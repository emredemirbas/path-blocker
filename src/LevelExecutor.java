import java.util.Stack;

public class LevelExecutor {

    public static void executeLevel(String filePath, String outputFolderName, Agent agent) {
        Level level = agent.getSolutionLevel(new Level(filePath));
        Stack<Level> stack = new Stack<>();

        // Push each parent level into the stack to trace back the solution path
        while (level.getParentLevel() != null) {
            stack.push(level);
            level = level.getParentLevel();
        }

        int count = 1;
        int totalDigits = 4;  // Get the number of digits to pad

        // Pop the levels and save images with formatted names
        while (!stack.isEmpty()) {
            Level poppedLevel = stack.pop();
            String formattedFileName = String.format(outputFolderName + "\\%0" + totalDigits + "d.png", count);
            LevelVisualizer.createAndSaveLevelImage(formattedFileName, poppedLevel);
            count++;
        }
    }
}

