
public class Tile {
	private int room, row, col;
	private char ch;
	private Tile prev;
	private boolean visited;
	
	// Constructor
	public Tile(int room, int row, int col) {
		this.room = room;
		this.row = row;
		this.col = col;
		ch = '.';
		prev = null;
		visited = false;
	}
	
	// Various getters/setters
	public void setChar(char ch) {
		this.ch = ch;
	}
	public char getChar() {
		return ch;
	}

	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public int getRoom() {
		return room;
	}

	public boolean isVisited() {
		return visited;
	}
	public boolean isValid() {
		return !visited && ch != '@';
	}
	
	public void setPrev(Tile prev) {
		visited = true;
		this.prev = prev;
	}
	public Tile getPrev() {
		return prev;
	}
}
