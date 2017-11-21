import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
	private LineSegment[] lineSegments;

	// finds all line segments containing 4 points
   	public BruteCollinearPoints(Point[] points) {
         Point[] copy = points.clone();
   		checkNull(points);
         Arrays.sort(copy);
         checkDuplicate(copy);
   		List<LineSegment> list = new LinkedList<>();
   		int n = points.length;

   		for (int p = 0; p < n - 3; p++) {
   			Point pp = copy[p];

   			for (int q = p + 1; q < n - 2; q++) {
   				Point pq = copy[q];
   				double slope1 = pp.slopeTo(pq);

   				for (int r = q + 1; r < n - 1; r++) {
   					Point pr = copy[r];
   					double slope2 = pp.slopeTo(pr);

   					if (slope1 != slope2) continue;
   					for (int s = r + 1; s < n; s++) {
   						Point ps = copy[s];
   						double slope3 = pp.slopeTo(ps);

   						if (slope1 != slope3) continue;
   						else list.add(new LineSegment(pp, ps));
    				}    				
    			}
   			}
   		}
   		lineSegments = list.toArray(new LineSegment[0]);
   	}

   	private void checkNull(Point[] points) {
   		if (points == null) 
   			throw new IllegalArgumentException("Input points array can not be null");
   		for (int i = 0; i < points.length; i++) {
   			if (points[i] == null)
   				throw new IllegalArgumentException("Points array contains null elements");
   		}

   	}
   	private void checkDuplicate(Point[] pts) {
   		for (int i = 0; i < pts.length - 1; i++) {
   			if (pts[i].compareTo(pts[i + 1]) == 0) throw new IllegalArgumentException("Input points array contians duplicate");
   		}
   	}

   	// the number of line segments
   	public int numberOfSegments() {
   		return lineSegments.length;
   	}

   	// the line segments   
   	public LineSegment[] segments() {
   		return lineSegments.clone();
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
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}