public class Main {
    public static void main(String[] args) {
        Level level = new Level("levels/level01/level01.txt");
        LevelImageGenerator.createAndSaveLevelImage("deneme.png", level);
    }
}