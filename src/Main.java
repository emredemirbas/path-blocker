import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;
public class Main {
    public static void main(String[] args) {
        Agent dfsagent = new DepthFirstSearchAgent();
        Agent idagent = new IterativeDeepeningSearchAgent();
        Agent bfsagent = new BreadthFirstSearchAgent();
        Agent ucsagent = new UniformCostSearchAgent();
        Path rootDir = Paths.get("levels");

        try (Stream<Path> folders = Files.list(rootDir)) {
            folders.filter(Files::isDirectory)
                    .forEach(folderPath -> {
                        System.out.println("Processing level: " + folderPath);
                        try (Stream<Path> files = Files.list(folderPath)) {
                            files.filter(file -> Files.isRegularFile(file) && file.toString().endsWith(".txt"))
                                    .forEach(txtFile -> {
                                        LevelExecutor.executeLevel(txtFile.toAbsolutePath().toString(), folderPath.toAbsolutePath().toString(), dfsagent);
                                        LevelExecutor.executeLevel(txtFile.toAbsolutePath().toString(), folderPath.toAbsolutePath().toString(), bfsagent);
                                        LevelExecutor.executeLevel(txtFile.toAbsolutePath().toString(), folderPath.toAbsolutePath().toString(), ucsagent);
                                        LevelExecutor.executeLevel(txtFile.toAbsolutePath().toString(), folderPath.toAbsolutePath().toString(), idagent);
                                        System.out.println("-".repeat(20));
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}