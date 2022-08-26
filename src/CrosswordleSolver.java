import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class CrosswordleSolver{
	
	private ArrayList<String> legalWords = new ArrayList<String>();
	
	private ArrayList<String> legalLetters;
	
	private String letters;
	
	public char[][] grid = new char[8][8];
	
	public Scanner kbd;
	
	private HashMap<String, Integer> counts = new HashMap<String, Integer>();
	
	private ArrayList<String> goodWords = new ArrayList<String>();
	
	
	public CrosswordleSolver(ArrayList<String> legalLetters, String wordDictionary, String countsData) throws IOException {
		
Scanner countReader = new Scanner(new FileReader(countsData));

		int counter = 0;
		
		while (countReader.hasNext()) {
			String information = countReader.next();
			
			String word = information.substring(0, information.indexOf(','));
			
			counts.put(word.toUpperCase(), counter);
		
			counter ++;
			
		}
		
		this.legalLetters = legalLetters;
		
		Scanner reader = new Scanner(new FileReader(wordDictionary));
		
		for (String letter : this.legalLetters) {
			letters += letter;
		}
		
		while (reader.hasNext()) {
			String newWord = reader.next();
			
			if (isMatch(letters, newWord)) {
				legalWords.add(newWord);
				
				try {
					if (counts.get(newWord) <= 40000 && newWord.length() >= 3) {
						goodWords.add(newWord);
					}
				}
				catch(Exception e) {
					System.out.println("PROBLEM " + newWord);
				}
			}
		}
		
		for (String word: goodWords) {
			System.out.println(word);
		}
		
		System.out.println(goodWords.size());
		
		
		
		this.kbd = new Scanner(System.in);
		
		
	}
	
	public boolean isMatch(String letters, String word) {
		
		for (int i = 0; i < word.length(); i ++) {
			if (letters.contains("" + word.charAt(i))) {
				
				int indexFound = letters.indexOf(word.charAt(i));
				
				letters = letters.substring(0, indexFound) + letters.substring(indexFound +1);
				
			}
			else {
				return false;
			}
			
		}
		
		return true;
		
	}
	
	
	public char[][] placeOnBoard(char[][] originalBoard, String word, int row, int col, boolean vertical) throws IllegalPlacementException {
		
		char[][] board = new char[8][8];
		
		for (int r = 0; r < 8; r ++) {
			for (int c = 0; c < 8; c ++) {
				board[r][c] = originalBoard[r][c];
			}
		}
		
		int shared = 0;
		
		if (vertical) {
			
			try {
				if (board[row-1][col] != ' ') {
					throw new IllegalPlacementException();
				}
			}
			catch (IndexOutOfBoundsException e) {
			}
			
			try {
				if (board[row+word.length()][col] != ' ') {
					throw new IllegalPlacementException();
				}
			}
			catch (IndexOutOfBoundsException e) {
			}
			
			for (int i = 0; i < word.length(); i ++) {
				
				char toBeReplaced = board[row+i][col];
				
				if (toBeReplaced != ' ' && toBeReplaced != word.charAt(i)) {
					throw new IllegalPlacementException();
				}
				
				else if (word.charAt(i) == toBeReplaced){
					shared ++;
					try {
						if (board[row+i+1][col] == word.charAt(i+1)) {
							throw new IllegalPlacementException();
						}
						
					}
					catch(IndexOutOfBoundsException e) {
					}
				}
				
				else {
					try {
						if (board[row+i][col+1] != ' ') {
							throw new IllegalPlacementException();
						}
						
						if (board[row+i][col-1] != ' ') {
							throw new IllegalPlacementException();
						}
						
					}
					catch(IndexOutOfBoundsException e) {
					}
				}
				
				board[row+i][col] = word.charAt(i);
				
			}
			
			
		}
		else{
			
			try {
				if (board[row][col-1] != ' ') {
					throw new IllegalPlacementException();
				}
			}
			catch (IndexOutOfBoundsException e) {
			}
			
			try {
				if (board[row][col+word.length()] != ' ') {
					throw new IllegalPlacementException();
				}
			}
			catch (IndexOutOfBoundsException e) {
			}
			
			for (int i = 0; i < word.length(); i ++) {
				
				char toBeReplaced = board[row][col+i];
				
				if (toBeReplaced != ' ' && toBeReplaced != word.charAt(i)) {
					throw new IllegalPlacementException();
				}
				
				else if (word.charAt(i) == toBeReplaced){
					shared ++;
					try {
						if (board[row][col+i+1] == word.charAt(i+1)) {
							throw new IllegalPlacementException();
						}
						
					}
					catch(IndexOutOfBoundsException e) {
					}
				}
				
				else {
					try {
						if (board[row+1][col+i] != ' ') {
							throw new IllegalPlacementException();
						}
						
						if (board[row-1][col+i] != ' ') {
							throw new IllegalPlacementException();
						}
						
					}
					catch(IndexOutOfBoundsException e) {
					}
				}
				
				board[row][col+i] = word.charAt(i);
				
			}
			
		}
		
		if (shared == 1 && shared < word.length()) {
			return board;
		}
		
		else {
			throw new IllegalPlacementException();
		}
	}
	
	
	public ArrayList<char[][]> allPlacements(char[][] board, String word) throws IllegalPlacementException {
		
		ArrayList<char[][]> allPossibilities = new ArrayList<char[][]>();
		
		for (int row = 0; row < 9 - word.length(); row ++) {
			
			for (int col = 0; col < 8; col ++) {
				
				try {
					char[][] newBoard = placeOnBoard(board.clone(), word, row, col, true);
					
					String usedLetters = "";
					
					for (int r = 0; r < 8; r ++) {
						for (int c = 0; c < 8; c ++) {
							if (letters.contains("" + newBoard[r][c])) {
								usedLetters += newBoard[r][c];
						
							}
						}
					}
					
					if (isMatch(letters, usedLetters)) {

						allPossibilities.add(newBoard.clone());
						
					}
					
					else {
						
						throw new IllegalPlacementException();
						
					}
					
				}
				
				catch (IllegalPlacementException e) {
				}
				
			}
			
		}
		
		for (int row = 0; row < 8; row ++) {
			
			for (int col = 0; col < 9 - word.length(); col ++) {
				
				try {
					char[][] newBoard = placeOnBoard(board.clone(), word, row, col, false);
					
					String usedLetters = "";
					
					for (int r = 0; r < 8; r ++) {
						for (int c = 0; c < 8; c ++) {
							usedLetters += newBoard[r][c];
						}
					}
					
					if (isMatch(letters, usedLetters)) {

						allPossibilities.add(newBoard.clone());
						
					}
					
					else {
						
						throw new IllegalPlacementException();
						
					}
					
				}
				
				catch (IllegalPlacementException e) {
				}
				
			}
			
		}
		
		return allPossibilities;
		
	}
	
	
	public char[][] depthFirstSearch(char[][] board, boolean first) throws IllegalPlacementException {
		
		if (isSolved(board)) {
			return board;
		}
		
		else {
			
			ArrayList<char[][]> possibleNextSteps = new ArrayList<char[][]>(); 
			
			for (String word : goodWords) {
				
				if (first) {
					
					if (word.length() >= 6) {
						
						try {
							char[][] newBoard = new char[8][8];
							
							for (int r = 0; r < 8; r ++) {
								for (int c = 0; c < 8; c ++) {
									newBoard[r][c] = board[r][c];
								}
							}
							
							
							int startCol = 4 - word.length()/2;
							
							for (int l = 0; l < word.length(); l ++) {
								newBoard[3][startCol + l] = word.charAt(l);
							}
							
							possibleNextSteps.add(newBoard);
						}
						catch(ArrayIndexOutOfBoundsException e) {
							
						}
					}
					
				}
				
				else {
					
					char[][] newBoard = new char[8][8];
					
					for (int r = 0; r < 8; r ++) {
						for (int c = 0; c < 8; c ++) {
							newBoard[r][c] = board[r][c];
						}
					}
					
					ArrayList<char[][]> waysToPlace = allPlacements(newBoard, word);
					
					for (char[][] nextStep : waysToPlace) {
						possibleNextSteps.add(nextStep);
					}
					
				}
					
				
			
			}
		
			for (char[][] nextStep : possibleNextSteps) {
				
				try {
					
					int nonSpace = 0;
					
					for (int r = 0; r < 8; r ++) {
						for (int c = 0; c < 8; c ++) {
							if (nextStep[r][c] != ' ') {
								nonSpace ++;
							}
						}
					}
					
					if (nonSpace == 12){
					
						for (char[]row : nextStep) {
							System.out.println(row);
						}
					}
					
					//String pause = kbd.next();
					
					return depthFirstSearch(nextStep, false);
					
				}
				
				catch(IllegalPlacementException e){
					
				}
				
			}
			
			throw new IllegalPlacementException();
			
		}
		
		
		
	}
	
	public boolean isSolved(char[][] board) {
		String usedLetters = "";
		
		for (int r = 0; r < 8; r ++) {
			for (int c = 0; c < 8; c ++) {
				usedLetters += board[r][c];
			}
		}
		
		if (isMatch(letters, usedLetters) && letters.length() == usedLetters.length()) {
			return true;
		}
		
		return false;
		
	}
	
	
	
}
