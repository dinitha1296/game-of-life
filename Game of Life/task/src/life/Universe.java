package life;

import java.util.Random;

class Universe {

    private boolean[][] grid;
    private final GenerationAlgorithm generationAlgorithm = new GenerationAlgorithm();
    private final Random random = new Random();
    private final GameOfLife view;

    Universe(GameOfLife view) {
        this.view = view;
        initiateNewUniverse();
    }

    int getNumOfAlive() {
        int sum = 0;
        for (boolean[] row : grid) {
            for (boolean alive: row) {
                sum += alive ? 1 : 0;
            }
        }
        return sum;
    }

    void initiateNewUniverse() {
        int gridSize = 320;
        grid = new boolean[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = random.nextBoolean();
            }
        }
        setView(1);
    }

    void generateNewGeneration(int iterationNumber) {
        grid = generationAlgorithm.generateNewGeneration(grid);
        setView(iterationNumber + 1);
    }

    private void setView(int generationNumber) {
        view.setGenerationNumber(generationNumber);
        view.setLifeCount(getNumOfAlive());
        view.reDrawGrid(grid);
    }
}