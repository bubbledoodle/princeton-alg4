import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

   private final double[] fractions;
   public PercolationStats(int n, int trials) {
 // perform trials independent experiments on an n-by-n grid
 		if (n <= 0) {
 			throw new java.lang.IllegalArgumentException("Invalid n number");
 		}

 		if (trials <= 0) {
 			throw new java.lang.IllegalArgumentException("Invalid trail number");
 		}
 		fractions = new double[trials];
 		for (int i = 0; i < trials; i++) {
 			Percolation percolation = new Percolation(n);
 			int openSites = 0;
 			while (!percolation.percolates()) {
 				int row = StdRandom.uniform(n) + 1;
 				int col = StdRandom.uniform(n) + 1;
 				if (!percolation.isOpen(row, col)) {
 					percolation.open(row, col);
 					openSites++;
 				}
 			}
 			fractions[i] = openSites * 1.0 / (n * n);
 		}
   }  

   public double mean() {
   	// sample mean of percolation threshold
   	return StdStats.mean(fractions);
   }         

   public double stddev() {
   	// sample standard deviation of percolation threshold
   	return StdStats.stddev(fractions);
   }                  

   public double confidenceLo() {
   	// low  endpoint of 95% confidence interval
   	return mean() - (1.96 * stddev() / Math.sqrt(fractions.length));
   }                 

   public double confidenceHi() {
   	// high endpoint of 95% confidence interval
   	return mean() + (1.96 * stddev() / Math.sqrt(fractions.length));
   }                 
   public static void main(String[] args) {
   	if (args.length != 2) {
   		System.out.println("Please input 2 arguments.");
   		return;
   	}
   	// test client (described below)
   	int n = Integer.parseInt(args[0]);
   	int trails = Integer.parseInt(args[1]);
   	PercolationStats stats = new PercolationStats(n, trails);
   	System.out.println("mean                    = " + stats.mean());
   	System.out.println("stddev                  = " + stats.stddev());
   	System.out.println("95% confidence interval = [" 
   				+ stats.confidenceLo() 
   				+ ", "
   				+ stats.confidenceHi()
   				+ "]");
   }       
}