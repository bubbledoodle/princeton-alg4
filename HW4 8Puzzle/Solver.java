import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Deque;
import java.util.LinkedList;

public class Solver {
	private MinPQ<SearchNode> pq;
	private SearchNode solutionNode;
	private boolean isSolvable;

	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	solutionNode = null;
    	pq = new MinPQ<>();
    	pq.insert(new SearchNode(initial, null, 0));

    	while (true) {
    		SearchNode currNode = pq.delMin();
	    	Board currBoard = currNode.getBoard();

	    	if (currBoard.isGoal()) {
	    		isSolvable = true;
	    		solutionNode = currNode;
	    		break;
	    	}

	    	if (currBoard.hamming() == 2 && currBoard.twin().isGoal()) {
	    		isSolvable = false;
	    		break;
	    	}

	    	int moves = currNode.getMoves();
	    	Board prevBoard = moves > 0 ? currNode.prev().getBoard() : null;
	    	for (Board nextBoard : currBoard.neighbors()) {
	    		if (nextBoard != null && nextBoard.equals(prevBoard)) {
	    			continue;
	    		}
	    		pq.insert(new SearchNode(nextBoard, currNode, moves + 1));
	    	}
    	}
    	
    } 

    // is the initial board solvable?         
    public boolean isSolvable() {
    	return isSolvable;
    } 

    // min number of moves to solve initial board; -1 if unsolvable  
    public int moves() {
    	if (!isSolvable) return -1;
    	return solutionNode.getMoves();
    }

    private class SearchNode implements Comparable<SearchNode> {
    	private final Board board;
    	private final SearchNode prev;
    	private final int moves;

    	public SearchNode(Board board, SearchNode prev, int moves) {
    		this.board = board;
    		this.prev = prev;
    		this.moves = moves;
    	}

    	@Override
    	public int compareTo(SearchNode that) {
    		return this.priority() - that.priority();
    	}

    	private int priority() {
    		return board.manhattan() + moves;
    	}

    	public Board getBoard() {
    		return board;
    	}

    	public int getMoves() {
    		return moves;
    	}

    	public SearchNode prev() {
    		return prev;
    	}
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	if (!isSolvable) return null;
    	Deque<Board> solution = new LinkedList<>();
    	SearchNode node = solutionNode;
    	while (node != null) {
    		solution.addFirst(node.getBoard());
    		node = node.prev();
    	}
    	return solution;
    } 

    // solve a slider puzzle (given below)  
    public static void main(String[] args) {
    	In in = new In(args[0]);
    	int n = in.readInt();
    	int[][] blocks = new int[n][n];
    	for (int row = 0; row < n; row++)
    		for (int col = 0; col < n; col++)
    			blocks[row][col] = in.readInt();
    	Board initial = new Board(blocks);
    	Solver solver = new Solver(initial);
    	if (!solver.isSolvable()) {
    		StdOut.println("Not Solvable");
    	} else {
    		StdOut.println("Minimum number of moves = " + solver.moves());
    		for (Board board : solver.solution()) {
    			StdOut.println(board);
    		}
    	}
    }
}
