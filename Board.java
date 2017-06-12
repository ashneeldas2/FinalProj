import java.util.ArrayList;
import java.util.LinkedList;
public class Board {
    
    private int[][] _board;
 
    public Board(int[][] board) {
        int N = board.length;
        _board = new int[N][N];
        for (int i = 0; i < _board.length; i++) {
            for (int j = 0; j < _board.length; j++) {
                _board[i][j] = board[i][j];
            }
        }
    }
    
    public int [][] getBoard() {
    	return _board;
    }

	public int hamming() {
		int solution = 0;
		for (int r = 0; r < _board.length; r++) {
			for (int c = 0; c < _board.length; c++) {
				if (notRight(r, c)) solution++;
			}
		}
		return solution;
	}
 
	private boolean notRight(int r, int c) {
		return (_board[r][c] != 0) && (_board[r][c] != rightPlace(r, c));
	}
	
    private int rightPlace(int r, int c) {
		return (r * _board.length) + c + 1;
	}
    
	public int manhattan() {
		int solution = 0;
		for (int r = 0; r < _board.length; r++) {
			for (int c = 0; c < _board.length; c++) {
				solution += distanceAway(r, c);
			}
		}
		return solution;
	}
	
	private int distanceAway(int r, int c) {
		if (_board[r][c] == 0) return 0;
		else {
			int index = _board[r][c];
			return (Math.abs(r - ((index - 1) / _board.length)) + Math.abs(c - ((index - 1) % _board.length)));
		}
	}
    
    public boolean isSolution() {
		for (int r = 0; r < _board.length; r++) {
			for (int c = 0; c < _board.length; c++) {
				if (r == c && c == 2 && _board[r][c] == 0) return true;
				if (_board[r][c] != 0 && !(_board[r][c] == (rightPlace(r, c)))) return false;
			}
		}
		return true;
	}

	public Board child() {
		for (int r = 0; r < _board.length; r++) {
			for (int c = 0; c < _board.length; c++) {
				if (_board[r][c] != 0 && _board[r][c + 1] != 0) return swap(r, c, r, c + 1); // Returns a new board with one swap
			}
		}
		return null;
	}
	
    private Board swap(int r, int c, int newR, int newC) {
    	Board ans = new Board(_board);
        int temp = ans._board[r][c];
        ans._board[r][c] = ans._board[newR][newC];
        ans._board[newR][newC] = temp;
        return ans;
    }
    
	public boolean equals(Object y) {
		if (y == this) return true;
		if (y != null || !(y instanceof Board) || ((Board) y)._board.length != _board.length) return false;
		for (int r = 0; r < _board.length; r++) {
			for (int c = 0; c < _board.length; c++) {
				if (((Board) y)._board[r][c] != _board[r][c]) return false;
			}
		}
		return true;
	}
	// return an Iterable of all neighboring board positions	                          
	public Iterable < Board > neighbors() {
		LinkedList < Board > neighbors = new LinkedList < Board > ();
		Integer[] spaces = locationOfSpace();
		if (spaces[0] > 0) neighbors.add(swap(spaces[0], spaces[1], spaces[0] - 1, spaces[1]));
		if (spaces[0] < _board.length - 1) neighbors.add(swap(spaces[0], spaces[1], spaces[0] + 1, spaces[1]));
		if (spaces[1] > 0) neighbors.add(swap(spaces[0], spaces[1], spaces[0], spaces[1] - 1));
		if (spaces[1] < _board.length - 1) neighbors.add(swap(spaces[0], spaces[1], spaces[0], spaces[1] + 1));
		return neighbors;
	}

	// Returns the first location of an empty space in the form of an Integer array
	private Integer[] locationOfSpace() {
		for (int r = 0; r < _board.length; r++) {
			for (int c = 0; c < _board.length; c++) {
				if (_board[r][c] == 0) {
					Integer[] location = new Integer[2];
					location[0] = r;
					location[1] = c;
					return location;
				}
			}
		}
		return null;
	}
 
    public String toString() {
    // string representation of this board (in the output format specified below)
        String s = "";
        for (int i = 0; i < _board.length; i++) {
        	for (int j = 0; j < _board[i].length; j++) {
        		s += _board[i][j] + " ";
        	}
        	s += "\n";
        }
        return s;
    }
}