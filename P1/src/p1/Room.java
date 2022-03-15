import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

public class Room {
	private int n, m;
	private Tile[][] grid;
	
	private int startR, startC;
	private int endR, endC;
			
	private final int dR[] = {-1, 1, 0, 0}, dC[] = {0, 0, 1, -1};
	
	// Constructor
	Room(int n, int m, int id) {
		this.n = n;
		this.m = m;
		
		grid = new Tile[n][m];
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j)
				grid[i][j] = new Tile(id, i, j);
		
		startR = startC = -1;
		endR = endC = -1;
	}
	
	// Check if position is in bounds
	public boolean inBounds(int row, int col) {
		return (row >= 0 && row < n && col >= 0 && col < m);
	}
	
	// Various getters/setters
	public void setStart(int r, int c) {
		startR = r;
		startC = c;
	}
	public void setEnd(int r, int c) {
		endR = r;
		endC = c;
	}
	public void setChar(int r, int c, char ch) {
		grid[r][c].setChar(ch);
	}
	public char getChar(int r, int c) {
		return grid[r][c].getChar();
	}
	
	// Check if a room is valid
	public boolean isValid() {
		return (startR != -1 && endR != -1);
	}
	
	// Run path-finding algorithm
	public boolean findPath(boolean bfs) {
		Deque<Tile> q = new LinkedList<Tile>();
		
		q.add(grid[startR][startC]);
		grid[startR][startC].setPrev(null);
		
		while (q.size() > 0) {
			Tile here = (bfs ? q.pollFirst() : q.pollLast());
			if (here.getChar() == '|' || here.getChar() == 'C')
				break;
			
			for (int dir = 0; dir < 4; ++dir) {
				int nRow = here.getRow() + dR[dir], nCol = here.getCol() + dC[dir];
				
				if (inBounds(nRow, nCol) && grid[nRow][nCol].isValid()) {
					q.add(grid[nRow][nCol]);
					grid[nRow][nCol].setPrev(here);
				}
			}
		}
		
		return grid[endR][endC].isVisited();
	}
	
	// Backtrack to retrieve solution
	public ArrayList<Tile> getSolution() {
		ArrayList<Tile> res = new ArrayList<Tile>();
		
		Tile cur = grid[endR][endC];
		while (cur != null) {
			if (cur.getChar() == '.')
				res.add(cur);
			cur = cur.getPrev();
		}
		
		Collections.reverse(res);
		return res;
	}
}
