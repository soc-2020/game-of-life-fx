package game_of_life;

import java.util.Random;


public class LifeModel {
    
    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    
    private int rows;
    private int cols;
    private int[][] grid;
    private Random random;
    
    public LifeModel(int rows, int cols) {
        random = new Random();
        this.rows = rows;
        this.cols = cols;
        grid = new int[rows][cols];
    }
    
    
    public void initialize(int probability) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int p = random.nextInt(100);
                grid[i][j] = p < probability ? ALIVE : DEAD;
            }
        }
    }
    
    
    public int cellAt(int row, int col) {
        return grid[row][col];
    }
    
    
    public void nextGeneration() {
        int[][] next = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int neighbors = aliveNeighbors(i, j);

                if (neighbors == 3) {
                    next[i][j] = ALIVE;
                }
                else if (neighbors < 2 || neighbors > 3) {
                    next[i][j] = DEAD;
                }
                else {
                    next[i][j] = grid[i][j];
                }
            }
        }
        grid = next;
    }
    
    
    
    private int aliveNeighbors(int row, int col) {
        int sum = 0;
        int startRow = row == 0 ? 0 : row-1;
        int endRow = row == rows - 1 ? 0 : row+1;
        int startCol = col == 0 ? 0 : col-1;
        int endCol = col == cols - 1 ? 0 : col+1;

        for (int k = startRow; k <= endRow; k++) {
            for (int m = startCol; m <= endCol; m++) {
                sum += grid[k][m];
            }
        }

        sum -= grid[row][col];

        return sum;
    }
    
}
