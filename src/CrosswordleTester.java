import java.io.IOException;
import java.util.*;


public class CrosswordleTester {
	
	public static void main (String [] args) throws IOException, IllegalPlacementException{
		
		// TODAY'S PUZZLE: wfbtldmekdnu
		
		
		ArrayList<String> letters = new ArrayList<String>();
		
		Scanner kbd = new Scanner(System.in);
		
		System.out.println("letters?");
		
		String legalLetters = kbd.next();
		
		for (int i = 0; i < 12; i ++) {
			letters.add(("" + legalLetters.charAt(i)).toUpperCase());
		}
		
		CrosswordleSolver solver = new CrosswordleSolver(letters, "Scrabble Word List", "Counts Data");
		
		
		char[][] testBoard = new char[8][8];
		
		for (int r = 0; r < 8; r ++) {
			for (int c = 0; c < 8; c ++) {
				testBoard[r][c] = ' ';
			}
		}
		
//		testBoard[3][1] = 'F';
//		testBoard[3][2] = 'L';
//		testBoard[3][3] = 'U';
//		testBoard[3][4] = 'N';
//		testBoard[3][5] = 'K';
//		testBoard[3][6] = 'E';
//		testBoard[3][7] = 'D';
		
		//ArrayList<char[][]> allPossibilities = solver.allPlacements(testBoard, "ABS");
		
		ArrayList<String> used = new ArrayList<String>();
		
		used.add("ABYS");
		
		try{
			char[][] board = solver.depthFirstSearch(testBoard, true);
		}
		catch (Exception e) {
		}
	}
	
}
