import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class P1 {
	private static Scanner sc;
		
	private static int n, m, k;
	private static Room[] rooms;
	private static char[][][] answer;
	
	private static boolean mapInput = true, mapOutput = true;
	private static boolean bfs = true;
	private static boolean showTime = true;
	
	private static long elapsedTime;
	
	private static ArrayList<Tile> solution;
	
	// Input checking
	private static void finish() {
		System.out.println("Invalid input.");
		System.exit(-1);
	}
	private static String checkNext() {
		if (!sc.hasNext())
			finish();
		return sc.next();
	}
	private static int checkNextInt() {
		if (!sc.hasNextInt())
			finish();
		return sc.nextInt();
	}
	
	// Finish the game
	private static void gameOver() {
		System.out.println("The cake is a lie.");
		System.exit(0);
	}
	
	// Read input sizes + reset map
	private static void readSize() {
		n = checkNextInt();
		m = checkNextInt();
		k = checkNextInt();
		
		rooms = new Room[k];
		for (int r = 0; r < k; ++r)
			rooms[r] = new Room(n, m, r);
	}
	
	// Read map input
	private static void readMap() {
		for (int r = 0; r < k; ++r) {
			for (int i = 0; i < n; ++i) {
				String here = checkNext();
				if (here.length() < m)
					finish();
										
				for (int j = 0; j < m; ++j) {
					char c = here.charAt(j);
					rooms[r].setChar(i, j, c);
					
					if (c == 'K')
						rooms[r].setStart(i, j);
					else if (c == '|' || c == 'C')
						rooms[r].setEnd(i, j);
					else if (c != '.' && c != '@')
						finish();
				}
			}
		}
	}
	
	// Read coordinate input
	private static void readCoords() {
		if (k > 1) {
			for (int r = 0; r < k; ++r) {
				for (int i = 0; i < n; ++i) {
					for (int j = 0; j < m; ++j) {
						char c = checkNext().charAt(0);
						int row = checkNextInt();
						int col = checkNextInt();
						
						if (row != i || col != j)
							finish();
						
						rooms[r].setChar(i, j, c);
						
						if (c == 'K')
							rooms[r].setStart(i, j);
						else if (c == '|' || c == 'C')
							rooms[r].setEnd(i, j);
						else if (c != '.' && c != '@')
							finish();
					}
				}
			}
		} else {
			while (sc.hasNext()) {
				char c = checkNext().charAt(0);
				int row = checkNextInt();
				int col = checkNextInt();
				if (!rooms[0].inBounds(row, col))
					finish();
				
				rooms[0].setChar(row, col, c);
				
				if (c == 'K')
					rooms[0].setStart(row, col);
				else if (c == '|' || c == 'C')
					rooms[0].setEnd(row, col);
				else if (c != '.' && c != '@')
					finish();
			}
		}
	}
	
	// Read the input
	private static void readInput(String filename) throws FileNotFoundException {
		sc = new Scanner(new File(filename));
		
		try {
			readSize();
			
			if (mapInput)
				readMap();
			else
				readCoords();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		sc.close();
	}
	
	// Find path using DFS/BFS
	private static void findPath() {
		long startTime = System.currentTimeMillis();
		
		for (int r = 0; r < k; ++r)
			if (!rooms[r].isValid())
				finish();
		
		solution = new ArrayList<Tile>();
		for (int r = 0; r < k; ++r) {
			if (rooms[r].findPath(bfs))
				solution.addAll(rooms[r].getSolution());
			else
				gameOver();
		}
		
		elapsedTime = System.currentTimeMillis() - startTime;
	}
	
	// Print the answer + runtime
	private static void printAnswer() {
		if (mapOutput) {
			answer = new char[k][n][m];
			for (int r = 0; r < k; ++r)
				for (int i = 0; i < n; ++i)
					for (int j = 0; j < m; ++j)
						answer[r][i][j] = rooms[r].getChar(i, j);
			
			for (Tile here : solution)
				answer[here.getRoom()][here.getRow()][here.getCol()] = '+';
			
			for (int r = 0; r < k; ++r) {
				for (int i = 0; i < n; ++i) {
					for (int j = 0; j < m; ++j)
						System.out.print(answer[r][i][j]);
					System.out.println();
				}
				System.out.println();
			}
		} else {
			for (Tile here : solution)
				System.out.println("+ " + here.getRow() + " " + here.getCol());
			System.out.println();
		}
		
		if (showTime)
			System.out.println("Total Runtime: " + elapsedTime / 1000.0 + " seconds");
	}

	public static void main(String[] args) throws FileNotFoundException {
		readInput("test.txt");
		findPath();
		printAnswer();
	}

}
