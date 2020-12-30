package assignment03;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Brute force, recursive implementation of the sudoku solver.
 * 
 * @author Alejandro Rubio
 * @author Alejandro Serrano
 *
 */
public class SudokuBacktrackRecursive implements Sudoku {
	/*
	 * Private fields and helper methods
	 */
	// variable to hold all the data in a 2d array
	private Integer[][] data;
	// length of the 2d array is the number of rows
	private int numRows;
	// length of the 2d array is the number of columns
	private int numColumns;

	/**
	 * Creates a new puzzle by reading a file.
	 *
	 * @param filename Relative path of the file containing the puzzle in the given
	 *                 format
	 * @requires The file must be 9 rows of 9 numbers separated by whitespace the
	 *           numbers should be 1-9 or 0 representing an empty square
	 */
	public SudokuBacktrackRecursive(String filename) {
		// read from file
		SimpleReader file = new SimpleReader1L(filename);
		// create a new 2d array with length 9 and 9
		data = new Integer[9][9];
		// get the length of the array
		numRows = data.length;
		// get the length of the array
		numColumns = data[0].length;
		// go trough all the lines of the file
		for (int i = 0; i < numRows; i++) {
			// grab the entire number from the file
			String bigNum = file.nextLine();
			// convert the number to characters and place them on another array
			char[] arrayy = bigNum.toCharArray();
			// loop to place items in columns and rows
			for (int j = 0; j < numColumns; j++) {
				// grab the number from the array
				char convert = arrayy[j];
				// convert the number to an Integer
				Integer num = Integer.parseInt(String.valueOf(convert));
				// place the number in the 2d array
				data[i][j] = num;
			}
		}
		// close the file
		file.close();
	}

	/**
	 * Determines whether the given {@code number} can be placed in the given
	 * {@code row} without violating the rules of sudoku.
	 *
	 * @param row    which row to see if the number can go into
	 * @param number the number of interest
	 *
	 * @requires [{@code row} is a valid row index] and [{@code number} is a valid
	 *           digit]
	 * 
	 * @return true iff it is possible to place that number in the row without
	 *         violating the rule of 1 unique number per row.
	 */
	public boolean isValidForRow(int row, int number) {
		assert 0 < number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";
		assert 0 <= row && row < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		// create variable to check if its valid for row or not
		boolean check = true;
		// go trough all the numbers from the row
		for (int i = 0; i < 9; i++) {
			// if the number equals the number from the row
			if (data[row][i] == number) {
				// the number is not valid for row
				check = false;
			}
		}
		// return if its valid for row or not
		return check;
	}

	/**
	 * Determines whether the given {@code number} can be placed in the given column
	 * without violating the rules of sudoku.
	 *
	 * @param col    which column to see if the number can go into
	 * @param number the number of interest
	 *
	 * @requires [{@code col} is a valid column index] and [{@code number} is a
	 *           valid digit]
	 * 
	 * @return true iff it is possible to place that number in the column without
	 *         violating the rule of 1 unique number per row.
	 */
	public boolean isValidForColumn(int col, int number) {
		assert 0 <= col && col < PUZZLE_HEIGHT_WIDTH : "Violation of valid column index";
		assert 0 < number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";
		// create variable to check if its valid for column or not
		boolean check = true;
		// go trough all the columns
		for (int i = 0; i < 9; i++) {
			// if the number equals the number from the column
			if (data[i][col] == number) {
				// the number is not valid for column
				check = false;
			}
		}
		// return if its valid for column or not
		return check;
	}

	/**
	 * Determines whether the given {@code number} can be placed in "box" starting
	 * at the given position without violating the rules of sudoku.
	 * 
	 * The positions marked # are the valid positions of start of a box. They are
	 * (0,0), (0,3), (0, 6), (3,0), (3,3), (3,6), (6,0), (6,3), (6,6).
	 * 
	 * <pre>
	 * #00|#00|#00|
	 * 000|000|000|
	 * 000|000|000|
	 * ---+---+---+
	 * #00|#00|#00|
	 * 000|000|000|
	 * 000|000|000|
	 * ---+---+---+
	 * #00|#00|#00|
	 * 000|000|000|
	 * 000|000|000|
	 * ---+---+---+
	 * </pre>
	 *
	 * @param boxStartRow row index at which the box of interest starts
	 * @param boxStartCol column index at which the box of interest starts
	 * @param number      the number of interest
	 *
	 * @requires [{@code boxStartRow} and {@code boxStartCol} are valid box start
	 *           indices] and [{@code number} is a valid digit]
	 *
	 * @return true iff it is possible to place that number in the column without
	 *         violating the rule of 1 unique number per row.
	 */
	public boolean isValidForBox(int boxStartRow, int boxStartCol, int number) {
		assert 0 < number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";
		assert boxStartRow % BOX_HEIGHT_WIDTH == 0 : "Violation of valid boxStartRow";
		assert boxStartCol % BOX_HEIGHT_WIDTH == 0 : "Violation of valid boxStartCol";
		// create variable to check if its valid for box or not
		boolean check = true;
		// create limit to search in 3 by 3 box
		int limit1 = boxStartRow + 3;
		// create limit to search in 3 by 3 box
		int limit2 = boxStartCol + 3;
		// go trough the box
		for (int i = boxStartRow; i < limit1; i++) {
			// go trough the box
			for (int j = boxStartCol; j < limit2; j++) {
				// if the number equals the number from the array
				if (data[i][j] == number) {
					// the number is not valid for box
					check = false;
				}
			}
		}
		// return if its valid for column or not
		return check;
	}

	/**
	 * Determines whether the given {@code number} can be placed in the given
	 * position without violating the rules of sudoku.
	 *
	 * @param row    which row to see if the number can go into
	 * @param col    which column to see if the number can go into
	 * @param number the number of interest
	 *
	 * @requires [{@code row} is a valid row index] and [{@code col} is a valid
	 *           column index] and [{@code number} is a valid digit]
	 * 
	 * @return true iff it is possible to place that number in the column without
	 *         violating the rule of 1 unique number per row.
	 */
	public boolean isValidForPosition(int row, int col, int number) {
		assert 0 <= row && row < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= col && col < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 < number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";
		// get the first row from the box
		int boxStartRow = (row / 3) * 3;
		// get the first column from the box
		int boxStartCol = (col / 3) * 3;
		// check if the number is valid for column, row and box
		if (this.isValidForColumn(col, number) && this.isValidForRow(row, number)
				&& this.isValidForBox(boxStartRow, boxStartCol, number)) {
			// return true if the number is valid
			return true;
		} else {
			// return false if the number is not valid
			return false;
		}
	}

	@Override
	public int element(int i, int j) {
		assert 0 <= i && i < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= j && j < PUZZLE_HEIGHT_WIDTH : "Violation of valid column index";
		// get the number in the position i and j from the array
		return data[i][j];
	}

	@Override
	public void setElement(int i, int j, int number) {
		assert 0 <= i && i < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= j && j < PUZZLE_HEIGHT_WIDTH : "Violation of valid column index";
		assert 0 <= number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";
		// set the number in the position i and j from the array
		data[i][j] = number;
	}

	@Override
	public boolean solve() {
		// go trough all the rows
		for (int i = 0; i < 9; i++) {
			// go trough all the columns
			for (int j = 0; j < 9; j++) {
				// if the cell does not have a fixed value
				if (data[i][j] == 0) {
					// try to put numbers from 1 to 9
					for (int y = 1; y < 10; y++) {
						// if its valid
						if (this.isValidForPosition(i, j, y)) {
							// set it
							this.setElement(i, j, y);
							// call solve again
							if (solve()) {
								return true;
							} else {
								// if its not valid for position return it to unassigned
								data[i][j] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean verify() {
		// create variable to check if the puzzle is completed
		boolean check = true;
		// go trough the rows
		for (int i = 0; i < 9; i++) {
			// go trough the columns
			for (int j = 0; j < 9; j++) {
				// check if the number is valid
				if (!this.isValidForPosition(i, j, data[i][j])) {
					// if a value from the puzzle is not correct the puzzle is not solved
					check = false;
				}
			}
		}
		// return if the puzzle is solved or not
		return check;
	}

	@Override
	public String toString() {
		// create variable for final output
		String str = "";
		// go trough all the rows
		for (int i = 0; i < numRows; i++) {
			// go trough all the columns
			for (int j = 0; j < numColumns; j++) {
				// if the element is 0
				if (element(i, j) == 0) {
					// add an empty space to the final output
					str = str + " " + "|";
				} else {
					// if the number is not 0 add the number to the final output
					str = str + element(i, j) + "|";
				}
			}
			// add a new line to the output
			str = str + "\n";
			// if the box ends
			if (i == 2 || i == 5 || i == 8) {
				// add a new column to divide sudoku boxes
				str = str + "-----+-----+-----+" + "\n";
			}
		}
		// return the puzzle
		return str;
	}

	/**
	 * Main method produces the following output. You can modify it to debug and
	 * refine your implementation.
	 * 
	 * <pre>
	===== Sudoku puzzle =====

	| |3| |2| |6| | |
	9| | |3| |5| | |1|
	| |1|8| |6|4| | |
	-----+-----+-----+
	| |8|1| |2|9| | |
	7| | | | | | | |8|
	| |6|7| |8|2| | |
	-----+-----+-----+
	| |2|6| |9|5| | |
	8| | |2| |3| | |9|
	| |5| |1| |3| | |
	-----+-----+-----+



	===== Solving sudoku =====


	4|8|3|9|2|1|6|5|7|
	9|6|7|3|4|5|8|2|1|
	2|5|1|8|7|6|4|9|3|
	-----+-----+-----+
	5|4|8|1|3|2|9|7|6|
	7|2|9|5|6|4|1|3|8|
	1|3|6|7|9|8|2|4|5|
	-----+-----+-----+
	3|7|2|6|8|9|5|1|4|
	8|1|4|2|5|3|7|6|9|
	6|9|5|4|1|7|3|8|2|
	-----+-----+-----+
	 * </pre>
	 * 
	 * @param args command line arguments, not used
	 */
	public static void main(String[] args) {
		// create new simplewriter for output
		SimpleWriter out = new SimpleWriter1L();
		// read from file
		Sudoku s = new SudokuBacktrackRecursive("data/sudoku1.txt");
		out.println("===== Sudoku puzzle =====\n");
		// print the sudoku unsolved
		out.println(s.toString());
		out.println("\n\n===== Solving sudoku =====\n\n");
		// solve the sudoku
		s.solve();
		// print the sudoku solved
		out.println(s);
		// close simplewriter
		out.close();
	}
}