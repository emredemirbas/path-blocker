import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LevelImageGenerator {
    private static final int CELL_SIZE = 40;
    public static void createAndSaveLevelImage(String path, Level level){
        // Create a BufferedImage object
        int width = CELL_SIZE * level.getWidth();
        int height = CELL_SIZE * level.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Get the Graphics2D object
        Graphics2D g2d = image.createGraphics();

        // Set the background color
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, width, height);


//        for(int[] wallPosition:level.getWallPositions()){
//            g2d.setColor(Color.BLACK);
//            int x = wallPosition[0]*cellSize;
//            int y = wallPosition[1]*cellSize;
//            g2d.fillRect(x,y, cellSize, cellSize);
//        }

        boolean[][] world = level.getWorld();
        for(int x=0; x<level.getWidth(); x++){
            for(int y=0; y<level.getHeight(); y++){
                if(world[x][y]){
                    g2d.setColor(Color.black);
                    int pixelCoordinateX = x* CELL_SIZE;
                    int pixelCoordinateY = y* CELL_SIZE;
                    g2d.fillRect(pixelCoordinateX, pixelCoordinateY, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        int count = 0; // Initialize a counter to keep track of the appearance order
        for (int[] agentPosition : level.getAgentPositions()) {
            g2d.setColor(Color.green);
            int pixelCoordinateX = agentPosition[0] * CELL_SIZE;
            int pixelCoordinateY = agentPosition[1] * CELL_SIZE;
            g2d.fillRect(pixelCoordinateX, pixelCoordinateY, CELL_SIZE, CELL_SIZE);

            // Set the color for the number text to contrast with the cell color
            g2d.setColor(Color.black);

            // Draw the number in the center of the cell
            g2d.drawString(String.valueOf(count), pixelCoordinateX + CELL_SIZE / 2, pixelCoordinateY + CELL_SIZE / 2);

            count++; // Increment the counter
        }

        g2d.setColor(Color.RED);
        int goalXPosition = level.getGoalPosition()[0] * CELL_SIZE;
        int goalYPosition = level.getGoalPosition()[1] * CELL_SIZE;

        g2d.fillRect(goalXPosition, goalYPosition, CELL_SIZE, CELL_SIZE);

        g2d.setColor(Color.yellow);
        int startXPosition = level.getStartPosition()[0] * CELL_SIZE;
        int startYPosition = level.getStartPosition()[1] * CELL_SIZE;

        g2d.fillRect(startXPosition, startYPosition, CELL_SIZE, CELL_SIZE);

        // Dispose of the Graphics2D object
        g2d.dispose();

        // Save the image as a PNG file
        try {
            File outputFile = new File(path);
            ImageIO.write(image, "png", outputFile);
            System.out.printf("Grid image saved as %s\n", path);
        } catch (IOException e) {
            System.err.println("Error saving the image: " + e.getMessage());
        }
    }
}
