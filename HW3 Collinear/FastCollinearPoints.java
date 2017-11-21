import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
	private LineSegment[] lineSegments;
	// finds all line segments containing 4 or more points
   	public FastCollinearPoints(Point[] points) {
         Point[] copy = points.clone();
   		checkNull(points);
         Arrays.sort(copy);
         checkDuplicate(copy);
   		int n = points.length;
   		List<LineSegment> list = new LinkedList<>();

   		for (int i = 0; i < n; i++) {
   			Point p = copy[i];
   			Point[] currSlopeOrderpts = copy.clone();

   			// sorted the array by slope
			   Arrays.sort(currSlopeOrderpts, p.slopeOrder());
   			for (int j = 1; j < n; j++) {
   				final double slope = p.slopeTo(currSlopeOrderpts[j]);
   				LinkedList<Point> candidates = new LinkedList<>();
   				while (j < n && p.slopeTo(currSlopeOrderpts[j]) == slope) {
   					candidates.add(currSlopeOrderpts[j++]);
   				}

               j--;
   				if (candidates.size() >= 3 && p.compareTo(candidates.peek()) < 0) {
   					Point starting = p;
   					Point ending = candidates.getLast();
   					list.add(new LineSegment(starting, ending));
   				} 
   			}
   		}
   		lineSegments = list.toArray(new LineSegment[0]);
   	}

   	// the number of line segments
   	public int numberOfSegments() {
   		return lineSegments.length;
   	}     

   	// the line segments
   	public LineSegment[] segments() {
   		return lineSegments.clone();
   	}  
 
   	private void checkNull(Point[] points) {
   		if (points == null) 
   			throw new IllegalArgumentException("Null points array");
   		for (Point p : points) {
   			if (p == null)
   				throw new IllegalArgumentException("array contians null");
   		}
   	}

   	private void checkDuplicate(Point[] points) {
   		for (int i = 0; i < points.length - 1; i++) {
   			if (points[i].compareTo(points[i + 1]) == 0) 
   				throw new IllegalArgumentException("Point array contains duplicate");
   		}
   	}

   	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}