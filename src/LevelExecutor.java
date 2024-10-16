import java.util.Arrays;
import java.util.Stack;

public class LevelExecutor {

    public static void executeLevel(String filePath, String outputFolderName, Agent agent) {
        Level solutionLevel = agent.getSolutionLevel(new Level(filePath));
        for (int[] arr : solutionLevel.getAgentPositions()) {
            System.out.println(Arrays.toString(arr));
        }
        Stack<Level> stack = new Stack<>();

        Level ptr = solutionLevel;
        while (ptr != null) {
            stack.push(ptr);
            ptr = ptr.getParentLevel();
        }

        int count = 0;
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

