import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;
public class Main {
    public static void main(String[] args) {

        Agent agent = new UniformCostSearchAgent();
        Path rootDir = Paths.get("levels");

        try (Stream<Path> folders = Files.list(rootDir)) {
            folders.filter(Files::isDirectory)
                    .forEach(folderPath -> {
                        System.out.println("Processing level: " + folderPath);
                        try (Stream<Path> files = Files.list(folderPath)) {
                            files.filter(file -> Files.isRegularFile(file) && file.toString().endsWith(".txt"))
                                    .forEach(txtFile -> {
                                        LevelExecutor.executeLevel(txtFile.toAbsolutePath().toString(), folderPath.toAbsolutePath().toString(), agent);
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