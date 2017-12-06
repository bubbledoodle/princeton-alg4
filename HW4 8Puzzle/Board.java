import java.util.List;
import java.util.LinkedList;

/**
 * Board
 * @author Shuaiyu Liang
 * @date 12/05/2017 
 */

public class Board {
    private final int[][] blocks;
    private final int n;
    private int blankRow;
    private int blankCol;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException();

        this.blocks = copy(blocks);
        n = blocks.length;
        blankRow = -1;
        blankCol = -1;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (blocks[row][col] == 0) {
                    blankRow = row;
                    blankCol = col;
                    return;
                }
            }
        }

    } 

    private int[][] copy(int[][] block) {
        int n = block.length;
        int [][] clone = new int[n][];
        for (int row = 0; row < n; row++) {
            clone[row] = block[row].clone();
        }
        return clone;
    }        

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == blankRow && col == blankCol) continue;
                if (manhattan(row, col) != 0) count++;
            }
        }
        return count;
    }  

    // sum of Manhattan distances between blocks and goal               
    public int manhattan() {
        int count = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == blankRow && col == blankCol) continue;
                count += manhattan(row, col);            }
        }
        return count;
    }  

    private int manhattan(int row, int col) {
        int goal = blocks[row][col] - 1;
        int goalRow = goal / n;
        int goalCol = goal % n;
        return Math.abs(goalRow - row) + Math.abs(goalCol - col);
    }

    // is this board the goal board?            
    public boolean isGoal() {
        return hamming() == 0;
    }    

    // a board that is obtained by exchanging any pair of blocks           
    public Board twin() {
        int[][] cloned = copy(blocks);
        if (blankRow != 0) {
            swap(cloned, 0, 0, 0, 1);
        } else {
            swap(cloned, 1, 0, 1, 1);
        }
        return new Board(cloned);

    }

    // swap two boards elements(rowA, colA) and (rowB, colB);
    private void swap(int[][] block, int rowA, int colA, int rowB, int colB) {
        int temp = block[rowA][colA];
        block[rowA][colA] = block[rowB][colB];
        block[rowB][colB] = temp;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.blankRow != that.blankRow) return false;
        if (this.blankCol != that.blankCol) return false;
        if (this.n != that.n) return false;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (blocks[row][col] != that.blocks[row][col]) return false;
            }
        }
        return true;
    }      

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();

        if (blankRow > 0) {
            int[][] north = copy(blocks);
            swap(north, blankRow - 1, blankCol, blankRow, blankCol);
            neighbors.add(new Board(north)); 
        }

        if (blankRow < n - 1) {
            int[][] south = copy(blocks);
            swap(south, blankRow + 1, blankCol, blankRow, blankCol);
            neighbors.add(new Board(south));
        }

        if (blankCol > 0) {
            int[][] west = copy(blocks);
            swap(west, blankRow, blankCol - 1, blankRow, blankCol);
            neighbors.add(new Board(west));
        }

        if (blankCol < n - 1) {
            int[][] east = copy(blocks);
            swap(east, blankRow, blankCol + 1, blankRow, blankCol);
            neighbors.add(new Board(east));
        }
        return neighbors;
    }   

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                sb.append(blocks[row][col]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    } 

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}