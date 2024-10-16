// TODO: txt file'ları tek bir folder içinde (ör. txtfiles); output png'leri ise farklı bir folder içinde (outputs) tutmak daha iyi olabilir.
public class Main {
    public static void main(String[] args) {
        Agent agent = new UniformCostSearchAgent();
        LevelExecutor.executeLevel("levels/level10/level10.txt", "levels/level10", agent);

    }
}