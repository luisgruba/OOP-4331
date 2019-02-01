package main.course.oop.tictactoe.util;
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
	private int[][] tiktak;
	private int r;
	private int c;
	private int dv;
	
	public TwoDArray(int rows, int cols, int defaultVal){
		/*TODO - Create a 2D integer array consisting of 
		 * the number of rows and columns given. Initialize 
		 * the array by setting each int to be the defaulVal. 
		 * */
		tiktak = new int[rows][cols];
		r = rows;
		c = cols;
		dv = defaultVal;

	}
	
	public void initArray(int defaultVal) {
		/*TODO - (Re)Initialize the array by 
		 * setting each int to be the defaulVal 
		 */
		for(int i = 0; i < tiktak.length; i++)
			for(int j = 0; j < tiktak[0].length; j++)
				tiktak[i][j] = defaultVal;
		
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
			return String.format("Failure: %d is not allowed");

		boolean success = (tiktak[row][col] == dv);

		tiktak[row][col] = (success) ? val : tiktak[row][col];

		return (success) ? String.format("Success! %d was inserted", val) : String.format("Failure: (%d, %d) is not empty", row, col);
		 
	}
	
	public int getInt(int row, int col) {
		/*TODO - Return the value at the specified row, col*/	 
		return tiktak[row][col];
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

<<<<<<< HEAD
		for(int i = 0; i < sudoku.length; i++)
			for(int j = 0; j < sudoku[0].length; j++)
				display += String.valueOf(sudoku[i][j]) + "\t";
=======
		for(int i = 0; i < tiktak.length; i++){
			for(int j = 0; j < tiktak[0].length; j++){
				display += String.valueOf(tiktak[i][j]) + "\t";
			}
>>>>>>> b886d54770dbf406df0e9744f41722a989a29856
			display += "\n";

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
<<<<<<< HEAD
		Hashtable details = new Hashtable();
		for(int i = 0; i < sudoku.length; i++){
			for(int j = 0; j < sudoku[0].length; j++){
				if(!details.containsKey(sudoku[i][j])){
					details.put(sudoku[i][j],1);
					count++;
				}
				else
					details.put(sudoku[i][j], (int)(details.get(sudoku[i][j])) + 1);
=======
		Hashtable<Integer, Integer> details = new Hashtable<Integer, Integer>();
		for(int i = 0; i < tiktak.length; i++){
			for(int j = 0; j < tiktak[0].length; j++){
				if(!details.containsKey(tiktak[i][j])){
					details.put(tiktak[i][j],1);
					count++;
				}
				else
					details.put(tiktak[i][j], details.get(tiktak[i][j]) + 1);
>>>>>>> b886d54770dbf406df0e9744f41722a989a29856
			}
		}

		String display = "Rows: " + r + "\n";
		display += "Columns: " + c + "\n";
		display += "There are " + count + "unique values\n";
		display += details.toString();

		return display;
	}		

}
