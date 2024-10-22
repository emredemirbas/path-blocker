import java.io.File;
import java.io.IOException;
import java.lang.annotation.AnnotationFormatError;
import java.nio.file.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Agent agent = new UniformCostSearchAgent();
        //Agent agent = new DepthFirstSearchAgent();
        //Agent agent = new BreadthFirstSearchAgent();
        //Agent agent = new IterativeDeepeningSearchAgent();

        Path rootDir = Paths.get("");

        try (Stream<Path> files = Files.list(rootDir)) {
            files.filter(file -> Files.isRegularFile(file) && file.toString().endsWith(".txt"))
                    .forEach(txtFile -> {
                        // Create a folder named after the txt file (without extension)
                        String folderName = txtFile.getFileName().toString().replaceFirst("\\.txt$", "");
                        Path folderPath = rootDir.resolve(folderName);

                        try {
                            Files.createDirectory(folderPath);
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