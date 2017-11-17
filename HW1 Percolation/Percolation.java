import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private final boolean[] isOpen;
   private final int n;
   private final int first, last;
   private final WeightedQuickUnionUF weightedUnion;
   private int numberOfOpenSites;

   public Percolation(int n) {
   		// create n-by-n grid, with all sites blocked
   		if (n <= 0) throw new java.lang.IllegalArgumentException("Invalid n number");

   		this.n = n;
   		int size = n * n;
   		// from 1 ~ size are grid
   		first = 0; 
   		last = size + 1;
   		weightedUnion = new WeightedQuickUnionUF(size + 2);
   		isOpen = new boolean[size + 2];
   		isOpen[first] = true;
   		isOpen[last] = true;
   		numberOfOpenSites = 0;
   }       

   private int getIndex(int row, int col) {
   		// check bound
   		if (row < 1 || row > n) {
   			throw new java.lang.IllegalArgumentException("Invalid row");
   		} else if (col < 1 || col > n) {
   			throw new java.lang.IllegalArgumentException("Invalid col");
   		} else {
   			return (row - 1) * n + col;
   		}
   		
   }

   private void tryUnion(int rowA, int colA, int rowB, int colB) {
   		if (rowB > 0 && rowB <= n && colB > 0 && colB <= n && isOpen[getIndex(rowB, colB)]) {
   			weightedUnion.union(getIndex(rowA, colA), getIndex(rowB, colB));
   		}
   		
   }

   public void open(int row, int col) {
   		// open site (row, col) if it is not open already

   		// set boolean mark;
   		// union with four neighboors;

   		if (isOpen[getIndex(row, col)]) return;
   		else {
   			isOpen[getIndex(row, col)] = true;
   			numberOfOpenSites++;
   		}
   		// try union indexing can't reach
   		if (row == 1) {
   			weightedUnion.union(getIndex(row, col), first);
   		}
   		if (row == n) {
   			weightedUnion.union(getIndex(row, col), last);
   		}

   		tryUnion(row, col, row - 1, col); // up
   		tryUnion(row, col, row + 1, col); // down
   		tryUnion(row, col, row, col - 1); // left
   		tryUnion(row, col, row, col + 1); // right
   }    

   public boolean isOpen(int row, int col) {
   		// is site (row, col) open?
   		return isOpen[getIndex(row, col)];
   } 

   public boolean isFull(int row, int col) {
   		// is site (row, col) full?
   		return weightedUnion.connected(getIndex(row, col), first);
   } 

   public int numberOfOpenSites() {
   		// number of open sites
   		return numberOfOpenSites;
   }  

   public boolean percolates() {
   		// does the system percolate?
   		return weightedUnion.connected(first, last);
   }             

   public static void main(String[] args) {
   		// test client (optional)
   }  
}
