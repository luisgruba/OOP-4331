package Objects;

import java.util.Scanner;

public class SuperTTTGame {

	private TTTGame[][] board;
	private Scanner sc;
	
	private int nextRow, nextCol;
	
	private boolean firstTurn;
	
	public SuperTTTGame() {
		
		board = new TTTGame[3][3];
		for(int i = 0; i < 3; i++) 
			for(int j = 0; j < 3; j++) 
				board[i][j] = new TTTGame(3);
		
		sc = new Scanner(System.in);
		firstTurn = true;
		nextRow = 0;
		nextCol = 0;
	}
	
	public int makeMove(Player player) {
		
		if(!board[nextRow][nextCol].isDone() && !firstTurn)	{	
			board[nextRow][nextCol].playTTT(player);
			
			int tempRow = nextRow;
			setNextRow(board[nextRow][nextCol].getRow());
			setNextCol(board[tempRow][nextCol].getCol());
			  
		}
		else {
			firstTurn = false;
			int userRow = setRow();
			int userCol = setCol();
			makeMove(player, userRow, userCol);
		}
		printAll();
		System.out.println(nextRow + ":" + nextCol);
		boolean gameEnd = isWin(player);
		System.out.println(gameEnd);
		if(!gameEnd && isFull()) {
			System.out.println("TIE BOARD");
			return 2;
		}
		else if(!gameEnd) {
			return 0;
		}
		else {
			System.out.println(player.getName() + " wins!");
			return 1;
		}
	}
	
	public void makeMove(Player player, int row, int col) {
		
		if(!board[row][col].isDone()) {
			board[row][col].playTTT(player);
				
			setNextRow(board[row][col].getRow());
			setNextCol(board[row][col].getCol());
		}
		else
			makeMove(player);
		
	}
	
	
	public int setRow() {
		System.out.print("Set new Row: (0-" + (board.length-1) +")");
		
		while(!sc.hasNextInt()) {
			System.out.println("Please enter an integer");
			sc.next();
		}
		
		return sc.nextInt();
	}
	
	public int setCol() {
		System.out.print("Set new Column: (0-" + (board.length-1) +")");
		
		while(!sc.hasNextInt()) {
			System.out.println("Please enter an integer");
			sc.next();
		}
		    
		return sc.nextInt();
	}
	
	public void printAll() {
		for(TTTGame[] rows: board)
			for(int i = 0; i <rows.length; i++) {
				System.out.println(i);
				rows[i].printBoard();
			}
			
	}
	
	public int getNextRow() {
		return nextRow;
	}
	
	public int getNextCol() {
		return nextCol;
	}
	
	public void setNextRow(int r) {
		nextRow = r;
	}
	
	public void setNextCol(int c) {
		nextCol = c;
	}
	
	public boolean isWin(Player player) {
		
		return  (board[0][0].getWinner() == player && board [0][1].getWinner() == player && board[0][2].getWinner() == player) ||
		        (board[0][0].getWinner() == player && board [1][1].getWinner() == player && board[0][0].getWinner() == player) ||
		        (board[0][0].getWinner() == player && board [1][0].getWinner() == player && board[0][0].getWinner() == player) ||
		        (board[2][0].getWinner() == player && board [2][1].getWinner() == player && board[2][0].getWinner() == player) ||
		        (board[2][0].getWinner() == player && board [1][1].getWinner() == player && board[0][0].getWinner() == player) ||
		        (board[0][2].getWinner() == player && board [1][2].getWinner() == player && board[0][2].getWinner() == player) ||
		        (board[0][1].getWinner() == player && board [1][1].getWinner() == player && board[0][1].getWinner() == player) ||
		        (board[1][0].getWinner() == player && board [1][1].getWinner() == player && board[1][0].getWinner() == player);
	}
	
	public boolean isFull() {
		int count = 0;
		for(int i = 0; i < 3; i++) 
			for(int j = 0; j < 3; j++) 
				if(board[i][j].isDone())
					count++;
		if(count == 9)
			return true;
		return false;
	}
}
