import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

/*
Answers to Questions

1) Why you prefer the search algorithm you choose?
We have chosen Uniform Cost Search (UCS) in this scenario because the game involves a path cost, and UCS is designed to
find the least-cost path in such cases. UCS expands the least-cost node first, ensuring that it will always find the
optimal solution without unnecessary state explorations.

2) Can you achieve the optimal result? Why? Why not?
Yes, we can achieve the optimal result with UCS. Since UCS expands nodes in order of their path cost, and the game is
acyclic, UCS is guaranteed to find the optimal path with the lowest cost.

3) How you achieved efficiency for keeping the states?
The tree version of UCS is used, which is efficient in terms of state management because it avoids storing repeated
states or cycles (since the game guarantees no cycles). Additionally, the use of a priority queue ensures that nodes are
expanded in the correct order, leading to optimal results without unnecessary expansions.

4) If you prefer to use DFS (tree version) then do you need to avoid cycles?
In the tree version of DFS, you do not need to avoid cycles because, the game mechanics prevent cycles from occurring.
This means that each path explored will be unique, and no node will be revisited.

5) What will be the path-cost for this problem?
The path cost in this problem is the number of blocks placed after each movement.
(see getMovementAmount() method in Level.java)
So, the total path cost will be the sum of the blocks placed for each move along the path from the start state to the
goal state.
*/

public class Main {
    public static void main(String[] args) {
        Agent agent = new UniformCostSearchAgent();

        Path rootDir = Paths.get("");

        try (Stream<Path> files = Files.list(rootDir)) {
            files.filter(file -> Files.isRegularFile(file) && file.toString().endsWith(".txt"))
                    .forEach(txtFile -> {
                        // Create a folder named after the txt file (without extension)
                        String folderName = txtFile.getFileName().toString().replaceFirst("\\.txt$", "");
                        Path folderPath = rootDir.resolve(folderName);

                        try {
                            if (Files.notExists(folderPath)) {
                                Files.createDirectory(folderPath);
                            }
                        } catch (IOException e) {
                            System.err.println("Failed to create folder: " + folderPath);
                            e.printStackTrace();
                            return;
                        }

                        // Execute the level and save output images to the created folder
                        LevelExecutor.executeLevel(txtFile.toAbsolutePath().toString(), folderPath.toAbsolutePath().toString(), agent);
                        System.out.println("-".repeat(20));
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}