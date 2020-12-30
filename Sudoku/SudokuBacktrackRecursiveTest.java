package assignment03;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for public methods of the {@code SudokuBacktrackRecursive}
 * class.
 * 
 * @author Alejandro Rubio
 * @author Alejandro Serrano
 *
 */
public class SudokuBacktrackRecursiveTest {
	private SudokuBacktrackRecursive s;
	private SudokuBacktrackRecursive s2;

	@Before // executed before every test case
	public void setUp() {
		s = new SudokuBacktrackRecursive("src/assignment03/sudoku1.txt");
		s2 = new SudokuBacktrackRecursive("src/assignment03/sudoku2.txt");
	}

	@Test
	public void number4IsValidAtRow0Col0() {
		final int number = 4;
		assertEquals(true, s.isValidForRow(0, number));
		assertEquals(true, s.isValidForColumn(0, number));
		assertEquals(true, s.isValidForBox(0, 0, number));
	}

	@Test
	public void number4IsNotValidAtRow2Col6() {
		final int number = 4;
		assertEquals(false, s.isValidForRow(2, number));
		assertEquals(false, s.isValidForColumn(6, number));
		assertEquals(false, s.isValidForBox(0, 6, number));
	}

	@Test
	public void is4ValidForPositionCol0Row0() {
		final int number = 4;
		assertEquals(true, s.isValidForPosition(0, 0, number));
	}

	@Test
	public void number4IsNotValidForBoxPosition() {
		final int number = 4;
		assertEquals(false, s.isValidForPosition(1, 7, number));
	}

	@Test
	public void testToString1() {
		assertEquals(" | |3| |2| |6| | |\n" + //
				"9| | |3| |5| | |1|\n" + //
				" | |1|8| |6|4| | |\n" + //
				"-----+-----+-----+\n" + //
				" | |8|1| |2|9| | |\n" + //
				"7| | | | | | | |8|\n" + //
				" | |6|7| |8|2| | |\n" + //
				"-----+-----+-----+\n" + //
				" | |2|6| |9|5| | |\n" + //
				"8| | |2| |3| | |9|\n" + //
				" | |5| |1| |3| | |\n" + //
				"-----+-----+-----+\n", s.toString());
	}

	@Test
	public void testSolve1() {
		s.solve();
		assertEquals("4|8|3|9|2|1|6|5|7|\n" + "9|6|7|3|4|5|8|2|1|\n" + "2|5|1|8|7|6|4|9|3|\n" + "-----+-----+-----+\n"
				+ "5|4|8|1|3|2|9|7|6|\n" + "7|2|9|5|6|4|1|3|8|\n" + "1|3|6|7|9|8|2|4|5|\n" + "-----+-----+-----+\n"
				+ "3|7|2|6|8|9|5|1|4|\n" + "8|1|4|2|5|3|7|6|9|\n" + "6|9|5|4|1|7|3|8|2|\n" + "-----+-----+-----+\n",
				s.toString());
	}

	@Test
	public void testToString2() {
		assertEquals("1| | |4|9| | | | |\n" + //
				"9|2| | | |5| |4| |\n" + //
				"8|4| | |6| | | |5|\n" + //
				"-----+-----+-----+\n" + //
				"4| | |5|2| |9| | |\n" + //
				" |6|9| | | |7|1| |\n" + //
				" | |1| |7|6| | |4|\n" + //
				"-----+-----+-----+\n" + //
				"6| | | |1| | |8|3|\n" + //
				" |1| |3| | | |5|9|\n" + //
				" | | | |5|4| | |1|\n" + //
				"-----+-----+-----+\n", s2.toString());
	}

	@Test
	public void testSolve2() {
		s2.solve();
		assertEquals("1|7|5|4|9|8|3|2|6|\n" + //
				"9|2|6|1|3|5|8|4|7|\n" + //
				"8|4|3|2|6|7|1|9|5|\n" + //
				"-----+-----+-----+\n" + //
				"4|3|7|5|2|1|9|6|8|\n" + //
				"5|6|9|8|4|3|7|1|2|\n" + //
				"2|8|1|9|7|6|5|3|4|\n" + //
				"-----+-----+-----+\n" + //
				"6|5|2|7|1|9|4|8|3|\n" + //
				"7|1|4|3|8|2|6|5|9|\n" + //
				"3|9|8|6|5|4|2|7|1|\n" + //
				"-----+-----+-----+\n", s2.toString());
	}

	@Test
	public void testElement1() {
		assertEquals(0, s.element(0, 0));
	}

	@Test
	public void testElement2() {
		assertEquals(3, s.element(0, 2));
	}

	@Test
	public void testSetElement1() {
		s.setElement(1, 2, 5);
		assertEquals(5, s.element(1, 2));
	}

	@Test
	public void testSetElement2() {
		s.setElement(1, 0, 3);
		assertEquals(3, s.element(1, 0));
	}
}