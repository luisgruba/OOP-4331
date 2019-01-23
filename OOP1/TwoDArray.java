//package main.course.oop.tictactoe.util;
import java.util.*;

/**
 * You must implement the following methods to accept the expected 
 * parameters and return accurate results.
 * You are allowed and expected to add any class attributes and methods 
 * needed to complete this assignment. 
 * 
 *
 */
public class TwoDArray {
	private int[][] sudoku;
	private int r;
	private int c;
	private int dv;
	
	public TwoDArray(int rows, int cols, int defaultVal){
		/*TODO - Create a 2D integer array consisting of 
		 * the number of rows and columns given. Initialize 
		 * the array by setting each int to be the defaulVal. 
		 * */
		sudoku = new int[rows][cols];
		r = rows;
		c = cols;
		dv = defaultVal;
		initArray(defaultVal);

	}
	
	public void initArray(int defaultVal) {
		/*TODO - (Re)Initialize the array by 
		 * setting each int to be the defaulVal 
		 */
		for(int i = 0; i < sudoku.length; i++)
			for(int j = 0; j < sudoku[0].length; j++)
				sudoku[i][j] = defaultVal;
		
	}
	
	public String insertInt(int row, int col, int val) {
		/*TODO - "Insert" based on the following conditions:
		 * 1. The location [row][col] is still set to the default value
		 * 		-return "Success! (val) was inserted.*/
		 /* 2. The location [row][col] is no longer the default value
		 * 		-return "Failure: (row), (col) is not empty.*/
		 /* 3. val is not the default value; 
		 * 		-return "Failure: (val) is not allowed."*/
		if(val == dv)
			return String.format("Failure: %d is not allowed", val);

		boolean success = (sudoku[row][col] == dv);

		sudoku[row][col] = (success) ? val : sudoku[row][col];

		return (success) ? String.format("Success! %d was inserted", val) : String.format("Failure: (%d, %d) is not empty", row, col);
		 
	}
	
	public int getInt(int row, int col) {
		/*TODO - Return the value at the specified row, col*/	 
		return sudoku[row][col];
	}
	
	public String getArrayDisplay() {
		/*TODO - Create a 2D display of the Array
		 * e.g. 
		 * 	1	0	1
		 *  0	1	0
		 *  0	1	1
		 * 
		 */
		String display = "";

		for(int i = 0; i < sudoku.length; i++){
			for(int j = 0; j < sudoku[0].length; j++){
				display += String.valueOf(sudoku[i][j]) + "\t";
			}
			display += "\n";
		}
		return display;
	}
	
	public String getArrayDetails() {
		/*TODO - List the following:
		 * # rows
		 * # columns
		 * How many unique values (in the above example, this would be 2
		 * Value and count of each (e.g. 
		 * 			value:1 count:5
		 * 			value:0 count:4
		 * 
		 * 			)
		 * 
		 */
		int count = 0;
		Hashtable<Integer, Integer> details = new Hashtable<Integer, Integer>();
		for(int i = 0; i < sudoku.length; i++){
			for(int j = 0; j < sudoku[0].length; j++){
				if(!details.containsKey(sudoku[i][j])){
					details.put(sudoku[i][j],1);
					count++;
				}
				else
					details.put(sudoku[i][j], details.get(sudoku[i][j]) + 1);
			}
		}

		String display = "Rows: " + r + "\n";
		display += "Columns: " + c + "\n";
		display += "Unique values " + count + "\n";
		display += details.toString();

		return display;
	}		

}