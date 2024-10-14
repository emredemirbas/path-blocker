import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LevelImageGenerator {
    private static final int cellSize = 40;
    public static void createAndSaveLevelImage(String path, Level level){
        // Create a BufferedImage object
        int width = cellSize * level.getWidth();
        int height = cellSize * level.getHeight();

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
                    int pixelCoordinateX = x*cellSize;
                    int pixelCoordinateY = y*cellSize;
                    g2d.fillRect(pixelCoordinateX, pixelCoordinateY, cellSize, cellSize);
                }
            }
        }

        g2d.setColor(Color.RED);
        int goalXPosition = level.getGoalPosition()[0] * cellSize;
        int goalYPosition = level.getGoalPosition()[1] * cellSize;

        g2d.fillRect(goalXPosition, goalYPosition, cellSize, cellSize);

        g2d.setColor(Color.yellow);
        int startXPosition = level.getStartPosition()[0] * cellSize;
        int startYPosition = level.getStartPosition()[1] * cellSize;

        g2d.fillRect(startXPosition, startYPosition, cellSize, cellSize);

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
