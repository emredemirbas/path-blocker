import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LevelVisualizer {

    private static final int cellSize = 20; // Adjust this as necessary

    public static void createAndSaveLevelImage(String path, Level level) {
        // Create the BufferedImage object
        BufferedImage image = createLevelImage(level);

        // Save the image as a PNG file
        try {
            File outputFile = new File(path);
            ImageIO.write(image, "png", outputFile);
            System.out.printf("Grid image saved as %s\n", path);
        } catch (IOException e) {
            System.err.println("Error saving the image: " + e.getMessage());
        }
    }

    public static void displayLevelImage(Level level) {
        // Create the BufferedImage object
        BufferedImage image = createLevelImage(level);

        // Create a JFrame to display the image
        JFrame frame = new JFrame("Level Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }

    private static BufferedImage createLevelImage(Level level) {
        // Create a BufferedImage object
        int width = cellSize * level.getWidth();
        int height = cellSize * level.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Get the Graphics2D object
        Graphics2D g2d = image.createGraphics();

        // Set the background color
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, width, height);

        // Draw walls (if needed)
        boolean[][] world = level.getWorld();
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                if (world[x][y]) {
                    g2d.setColor(Color.BLACK);
                    int pixelCoordinateX = x * cellSize;
                    int pixelCoordinateY = y * cellSize;
                    g2d.fillRect(pixelCoordinateX, pixelCoordinateY, cellSize, cellSize);
                }
            }
        }

        // Draw agents with their order numbers
        int count = 1; // Initialize a counter to keep track of the appearance order
        for (int[] agentPosition : level.getAgentPositions()) {
            g2d.setColor(Color.GREEN);
            int pixelCoordinateX = agentPosition[0] * cellSize;
            int pixelCoordinateY = agentPosition[1] * cellSize;
            g2d.fillRect(pixelCoordinateX, pixelCoordinateY, cellSize, cellSize);

            // Set the color for the number text to contrast with the cell color
            g2d.setColor(Color.BLACK);

            // Draw the number in the center of the cell
            g2d.drawString(String.valueOf(count), pixelCoordinateX + cellSize / 2 - 5, pixelCoordinateY + cellSize / 2 + 5); // Adjusted for better centering

            count++; // Increment the counter
        }

        // Draw the goal position
        g2d.setColor(Color.RED);
        int goalXPosition = level.getGoalPosition()[0] * cellSize;
        int goalYPosition = level.getGoalPosition()[1] * cellSize;
        g2d.fillRect(goalXPosition, goalYPosition, cellSize, cellSize);

        // Draw the start position
        g2d.setColor(Color.YELLOW);
        int startXPosition = level.getStartPosition()[0] * cellSize;
        int startYPosition = level.getStartPosition()[1] * cellSize;
        g2d.fillRect(startXPosition, startYPosition, cellSize, cellSize);

        // Dispose of the Graphics2D object
        g2d.dispose();

        return image; // Return the generated image
    }
}
