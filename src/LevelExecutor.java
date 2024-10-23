import java.util.Arrays;
import java.util.Stack;

public class LevelExecutor {

    public static void executeLevel(String filePath, String outputFolderName, Agent agent) {
        long startTime = System.nanoTime();
        Level solutionLevel = agent.getSolutionLevel(new Level(filePath));
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        System.out.printf("Searching for %s...\n", filePath);
        System.out.println("Elapsed time for search: " + elapsedTimeInSeconds + " seconds");

        if(solutionLevel == null){
            System.out.println("No solution found.");
            return;
        }else{
            System.out.println("Solution found!");
        }
        for (int[] arr : solutionLevel.getAgentPositions()) {
            //System.out.println(Arrays.toString(arr));
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
        System.out.println("Search steps are visualizing...");
        while (!stack.isEmpty()) {
            Level poppedLevel = stack.pop();
            String formattedFileName = String.format(outputFolderName + "\\%0" + totalDigits + "d.png", count);
            LevelVisualizer.createAndSaveLevelImage(formattedFileName, poppedLevel);
            count++;
        }
        System.out.println("Done!");
    }
}

