import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Generator {
			
	private static int rand(int lo, int hi) {
		return (int)(Math.random() * (hi - lo + 1)) + lo;
	}

	public static void main(String[] args) throws IOException {
		int n = rand(3, 20);
		int m = rand(3, 20);
		int k = rand(1, 5);
				
		boolean hasBorder = false;
		if (n > 3 && m > 3)
			hasBorder = rand(1, 1000) % 4 == 0;
		
		boolean hasCake = rand(1, 1000) % 10 != 0;
		boolean mapInput = rand(1, 1000) % 2 == 0;
		
		char[][][] g = new char[k][n][m];
		for (int r = 0; r < k; ++r)
			for (int i = 0; i < n; ++i)
				for (int j = 0; j < m; ++j)
					g[r][i][j] = '.';
		
		for (int r = 0; r < k; ++r) {
			// Add possible positions + fill in border
			ArrayList<Integer> pos = new ArrayList<Integer>();
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
					if (hasBorder) {
						if (i == 0 || i == n - 1 || j == 0 || j == m - 1)
							g[r][i][j] = '@';
						else
							pos.add(i * m + j);
					} else {
						pos.add(i * m + j);
					}
				}
			}
			
			// Put in cake
			if (hasCake && r == k - 1) {
				int ind = rand(0, pos.size() - 1);
				int cake = pos.remove(ind);
				g[r][cake / m][cake % m] = 'C';
			}
			
			// Put in Kirby
			boolean hasKirby = rand(1, 1000) % 100 != 0;
			if (hasKirby) {
				int ind = rand(0, pos.size() - 1);
				int kirby = pos.remove(ind);
				g[r][kirby / m][kirby % m] = 'K';
			}
			
			// Put in doors
			boolean hasDoor = (r != k - 1) && (rand(1, 1000) % 100 != 0);
			if (hasDoor) {
				int ind = rand(0, pos.size() - 1);
				int door = pos.remove(ind);
				g[r][door / m][door % m] = '|';
			}
			
			// Fill up extra spaces with borders
			int targ = pos.size() / rand(3, 5);
			while (targ-- > 0) {
				int ind = rand(0, pos.size() - 1);
				int barrier = pos.remove(ind);
				g[r][barrier / m][barrier % m] = '@';
			}
		}
		
		// Make the input invalid by adding an illegal character
		boolean makeInvalid = rand(1, 1000) % 100 == 0;
		if (makeInvalid)
			g[rand(0, k - 1)][rand(0, n - 1)][rand(0, m - 1)] = '$';
		
		// Map or coordinate input?
		FileWriter out = new FileWriter("test.txt");
		
		out.write(n + " " + m + " " + k + "\n");
		if (mapInput) {
			for (int r = 0; r < k; ++r) {
				for (int i = 0; i < n; ++i) {
					for (int j = 0; j < m; ++j)
						out.write(g[r][i][j]);
					out.write("\n");
				}
			}
		} else {
			for (int r = 0; r < k; ++r)
				for (int i = 0; i < n; ++i)
					for (int j = 0; j < m; ++j)
						if (k > 1 || (g[r][i][j] != '.'))
							out.write(g[r][i][j] + " " + i + " " + j + "\n");
		}
		
		out.close();
	}

}
