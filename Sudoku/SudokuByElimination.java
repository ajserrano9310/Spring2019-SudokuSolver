package assignment03;

import java.util.ArrayList;
import java.util.Iterator;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Iterative implementation of the sudoku solver.
 * 
 * @author Alejandro Rubio
 * @author Alejandro Serrano
 *
 */
public class SudokuByElimination implements Sudoku {
	// ArrayList of type Set Integer to hold the data
	private ArrayList<Set<Integer>> data;

	/**
	 * Creates a new puzzle by reading a file.
	 *
	 * @param filename Relative path of the file containing the puzzle in the given
	 *                 format
	 * @requires The file must be 9 rows of 9 numbers separated by whitespace the
	 *           numbers should be 1-9 or 0 representing an empty square
	 */
	public SudokuByElimination(String filename) {
		// read from file
		SimpleReader file = new SimpleReader1L(filename);
		// create new array list
		data = new ArrayList<Set<Integer>>();
		// 81 times loop for sets creation
		for (int x = 0; x < 81; x++) {
			// create 81 sets
			Set1L<Integer> set = new Set1L<>();
			// add the set to the arraylist
			data.add(set);
			// numbers from 1 to 9
			for (int y = 1; y < 10; y++) {
				// add them to the set
				set.add(y);
			}
		}
		// create arraylist for numbers from file
		ArrayList<Integer> s = new ArrayList<>();
		// go trough all the lines of the file
		for (int i = 0; i < 9; i++) {
			// get the entire number from the line
			String bigNum = file.nextLine();
			// convert the number to characters and place them on another array
			char[] array = bigNum.toCharArray();
			// loop to place items in arraylist
			for (int j = 0; j < 9; j++) {
				// get the number we want to convert
				char convert = array[j];
				// convert the number to an integer
				Integer num = Integer.parseInt(String.valueOf(convert));
				// add the number to the arraylist
				s.add(num);
			}
		}
		// start from row 0
		int row = 0;
		// start from column 0
		int col = 0;
		// go trough all the sets
		for (int a = 0; a < 81; a++) {
			// if the number from the arraylist is not 0
			if (!s.get(a).equals(0)) {
				// set the element
				this.setElement(row, col, s.get(a));
			}
			// if the row is 8
			if (row == 8) {
				// go to new column
				col = col + 1;
				// start at row 0
				row = 0;
			} else {
				// if the row is no 8 go up one row
				row = row + 1;
			}
		}
		// close the file
		file.close();
	}

	/**
	 * Eliminates the {@code element} from {@code row}, {@code col}, and Box
	 * containing position (row,col).
	 * 
	 * @param row     row index
	 * @param col     column index
	 * @param element to be eliminated
	 * 
	 * @requires [{@code row} and {@code col} are valid indices]
	 */
	public void eliminate(int row, int col, int element) {
		assert 0 <= row && row < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= col && col < PUZZLE_HEIGHT_WIDTH : "Violation of valid col index";
		// if the element is not a fixed value
		if (element != 0) {
			// eliminate the number from the row
			this.eliminateFromRow(row, element);
			// eliminate the number from the column
			this.eliminateFromColumn(col, element);
			// eliminate the number from the box
			this.eliminateFromBox(this.getBoxIndex(row, col), element);
		}
	}

	@Override
	public int element(int row, int col) {
		assert 0 <= row && row < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= col && col < PUZZLE_HEIGHT_WIDTH : "Violation of valid col index";
		// get the index of the set
		int index = (col * 9) + row;
		// if the set has multiple values
		if (data.get(index).size() > 1) {
			// return the value 0
			return 0;
		} else {
			// if the set has one value
			Iterator<Integer> itl = data.get(index).iterator();
			// return it
			return itl.next();
		}
	}

	@Override
	public void setElement(int row, int col, int number) {
		assert 0 <= row && row < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= col && col < PUZZLE_HEIGHT_WIDTH : "Violation of valid col index";
		assert 0 <= number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid number";
		// get the index of the set
		int index = (col * 9) + row;
		// start iterator to get values inside set
		Iterator<Integer> itl = data.get(index).iterator();
		// while the set has a valid value
		while (itl.hasNext()) {
			// set next to the next value of the set
			Integer next = itl.next();
			// if the value is not the same as the number
			if (!next.equals(number)) {
				// remove it
				itl.remove();
			}
		}
	}

	/**
	 * Eliminates the given number from each set in the given row.
	 * 
	 * @param row
	 * @param number
	 */
	private void eliminateFromRow(int row, int number) {
		// set the row as the first limit for the loop
		int limit1 = row;
		// set 81 as the second limit because we have 81 sets
		int limit2 = 81;
		// loop trough all elements from the given row
		for (int i = limit1; i < limit2; i = i + 9) {
			// if the set has more than 1 element
			if ((data.get(i).size() > 1)) {
				// start the iterator to go trough the values of the set
				Iterator<Integer> itl = data.get(i).iterator();
				// while the iterator has more numbers
				while (itl.hasNext()) {
					// set the number to a variable
					Integer next = itl.next();
					// if the number given is the same
					if (number == next) {
						// remove it
						itl.remove();
					}
				}
			}
		}
	}

	/**
	 * Eliminates the given number from each set in the given column.
	 * 
	 * @param col
	 * @param number
	 */
	private void eliminateFromColumn(int col, int number) {
		// set the first set of column as limit1
		int limit1 = col * 9;
		// set the limit2 to go trough the 9 elements
		int limit2 = limit1 + 9;
		// loop trough all elements in given column
		for (int i = limit1; i < limit2; i++) {
			// if the set has more than 1 element
			if ((data.get(i).size() > 1)) {
				// start the iterator to go trough the values of the set
				Iterator<Integer> itl = data.get(i).iterator();
				// while the iterator has more numbers
				while (itl.hasNext()) {
					// set the number to a variable
					Integer next = itl.next();
					// if the number given is the same
					if (number == next) {
						// remove it
						itl.remove();
					}
				}
			}
		}
	}

	/**
	 * Eliminates the given number from each set inside the box.
	 * 
	 * @param col
	 * @param number
	 */
	private void eliminateFromBox(int boxNumber, int number) {
		// limit of the first box
		int limit1 = boxNumber + 3;
		// limit of the second box
		int limit2 = boxNumber + 3 + 9;
		// limit of the third box
		int limit3 = boxNumber + 3 + 18;
		// go trough the first line of the box
		for (int i = boxNumber; i < limit1; i++) {
			// if the size of the set is more than 1
			if ((data.get(i).size() > 1)) {
				// start the iterator to go trough the values of the set
				Iterator<Integer> itl = data.get(i).iterator();
				// while the iterator has a next value
				while (itl.hasNext()) {
					// set the next value to a variable
					Integer next = itl.next();
					// if the number is equal to the given number
					if (number == next) {
						// remove it
						itl.remove();
					}
				}
			}
			// go trough the second line of the box
			for (int j = boxNumber + 9; j < limit2; j++) {
				// if the size of the set is more than 1
				if (data.get(j).size() > 1) {
					// start the iterator to go trough the values of the set
					Iterator<Integer> itl = data.get(j).iterator();
					// while the iterator has a next value
					while (itl.hasNext()) {
						// set the next value to a variable
						Integer next = itl.next();
						// if the number is equal to the given number
						if (number == next) {
							// remove it
							itl.remove();
						}
					}
				}
			}
			// go trough the third line of the box
			for (int k = boxNumber + 18; k < limit3; k++) {
				// if the size of the set is more than 1
				if (data.get(k).size() > 1) {
					// start the iterator to go trough the values of the set
					Iterator<Integer> itl = data.get(k).iterator();
					// while the iterator has a next value
					while (itl.hasNext()) {
						// set the next value to a variable
						Integer next = itl.next();
						// if the number is equal to the given number
						if (number == next) {
							// remove it
							itl.remove();
						}
					}
				}
			}
		}

	}

	/**
	 * Gets the number of the first set from the box
	 * 
	 * @param row
	 * @param col
	 * @return index of the first set from the box
	 */
	private int getBoxIndex(int row, int col) {
		// divide the row by 3 and then multiply it by 3
		row = (row / 3) * 3;
		// divide the column by 3 and then multiply it by 3
		col = (col / 3) * 3;
		// index of the set
		int index = (col * 10) - col + row;
		// return the index of the first set on the box
		return index;
	}

	@Override
	public boolean solve() {
		// while the puzzle is not completed
		while (!verify()) {
			// set row to 0
			int row = 0;
			// set column to 0
			int col = 0;
			// go trough all sets
			for (int i = 0; i < 81; i++) {
				// try to eliminate the number from the row, col and box
				this.eliminate(row, col, this.element(row, col));
				// if the row is 8
				if (row == 8) {
					// go to a new column
					col = col + 1;
					// start the row from 0
					row = 0;
				} else {
					// if row is not 8 keep row going
					row = row + 1;
				}
			}
		}
		return true;
	}

	@Override
	public boolean verify() {
		// variable to check if the puzzle is verified
		boolean check = true;
		// go trough 81 sets
		for (int i = 0; i < 81; i++) {
			// if the size of the set is more than 1
			if (data.get(i).size() > 1) {
				// the puzzle is not solved
				check = false;
			}
		}
		// return if the puzzle is solved or not
		return check;
	}

	@Override
	public String toString() {
		// create counter
		int count = 1;
		// create variable for final output
		String str = "";
		// go trough all the sets
		for (int i = 0; i < 81; i++) {
			// if the size of the set is more than 1
			if (data.get(i).size() > 1) {
				// add a space to the final output
				str = str + " " + "|";
			} else {
				// if is more than 1 create iterator
				Iterator<Integer> itl = data.get(i).iterator();
				// while the iterator has more values
				while (itl.hasNext()) {
					// assign the value to a variable
					Integer next = itl.next();
					// add the value to the final output
					str = str + next + "|";
				}
			}
			// if its in row 8
			if (count % 9 == 0) {
				// go to a new line
				str = str + "\n";
			}
			// increase the counter by 1
			count = count + 1;
			// if the box ends
			if (i == 26 || i == 53 || i == 80) {
				// add a new column to divide sudoku boxes
				str = str + "-----+-----+-----+" + "\n";
			}
		}
		// return the puzzle
		return str;
	}

	/**
	 * Main method, similar to the one in the other class.
	 * 
	 * @param args command line arguments, not used
	 */
	public static void main(String[] args) {
		// create new simplewriter for output
		SimpleWriter out = new SimpleWriter1L();
		// read from file
		Sudoku s = new SudokuByElimination("data/sudoku1.txt");
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