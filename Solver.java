import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Solver {
	
    private PriorityQueue<Move> pq = new PriorityQueue<Move>();
    private Move bestMove;
    private int minMoves = -1;
    private boolean solvable;
 
    private class Move implements Comparable<Move> {
        private Move _previous;
        private Board _current;
        private int _numMoves;
 
        public Move(Board board, Move previous) {
            _current = board;
            _previous = previous;
            if (_previous == null) 
            	_numMoves = 0;
            else
            	_numMoves = _previous._numMoves + 1;
        }
        
        public int compareTo(Move compare) {
			return (_current.manhattan() - compare._current.manhattan()) + (_numMoves - compare._numMoves);
		}
    }
    // constructor 
    public Solver(Board initial) {
    	// This uses the A Star Algorithm
        pq.add(new Move(initial, null)); 
        pq.add(new Move(initial.child(), null));
        while (!pq.isEmpty()) {
            Move current = pq.poll();
            if (current._current.isSolution()) {
                Move first = root(current);
                if (!first._current.equals(initial)) break;
                solvable = true;
                if (minMoves == -1 || current._numMoves < minMoves) {
                    minMoves = current._numMoves;
                    bestMove = current;
                }
            } 
            if (minMoves == -1 || current._numMoves + current._current.manhattan() < minMoves) {
                Iterable<Board> itr = current._current.neighbors();
                for (Board b : itr) {
                    if (current._previous == null || !b.equals(current._previous._current)) pq.add(new Move(b, current));
                }
            } 
            else break;
        }
    }
    // gets the first move, used in A Star Algorithm
    private Move root(Move move) {
        Move current = move;
        while (current._previous != null) {
            current = current._previous;
        }
        return current;
    }
    // is it solvable? 
    public boolean isSolvable() {
        return solvable;
    }
    // minimum num of moves to solve it, which is -1 if not possible.
    public int moves() {
        return minMoves;
    }
    // returns an iterable of the solution
    
    
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> moves = new Stack<Board>();
        while(bestMove != null) {
            moves.push(bestMove._current);
            bestMove = bestMove._previous;
        }

        return moves;
    }
 
    public static void main(String[] args) throws FileNotFoundException {
    // # of rows and columns
    	Scanner input = new Scanner(new File(args[0]));
		int rows = 0;
		int columns = 0;
		while (input.hasNextLine()) {
			rows++;
			Scanner colReader = new Scanner(input.nextLine());
			while (colReader.hasNextInt()) {
				columns++;
				colReader.nextLine();
			}
		}
		int[][] initial = new int[rows][columns];

		input.close();
		// actual insert of data
		input = new Scanner(new File(args[0]));
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (input.hasNextInt()) {
					initial[i][j] = input.nextInt();
				}
			}
		}
		// solves the board
        Solver solver = new Solver(new Board(initial));
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            for (Board board : solver.solution()) 
                System.out.println(board);
        }
    }
}