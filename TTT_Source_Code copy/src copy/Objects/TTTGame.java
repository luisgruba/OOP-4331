package Objects;

import java.util.Scanner;

public class TTTGame {

	private Board board;
	private Scanner sc;
	private Player winner;
	
	private boolean gameEnd;
	private boolean isDone;
	
	private int row;
	private int col;
	
	public TTTGame(int size) {
		sc = new Scanner(System.in);
		
		gameEnd = false;
		board = new Board(size);
		row = 0;
		col = 0;
		isDone = false;
		winner = null;
	}
	
	public int playTTT(Player currPlayer) {
		
		boolean valid = false;
		
		System.out.println(currPlayer.getName() + ", enter coordinates");
			
		setRow();
		setCol();
			
		valid = board.insert(row, col, currPlayer);
			
		while(!valid) {
			System.out.println(currPlayer.getName() + ", enter coordinates");
				
			setRow();
			setCol();
	
			valid = board.insert(row, col, currPlayer);
		}
		gameEnd = board.isWin(row, col, currPlayer.getPosition());
		//board.printBoard();
			
		if(!gameEnd && board.isFull()) {
			System.out.println("TIE BOARD");
			setDone(null);
			return 2;
		}
		else if(!gameEnd) {
			return 0;
		}
		else {
			board.setNull();
			System.out.println(currPlayer.getName() + " wins this board!");
			setDone(currPlayer);
			return 1;
		}
			
	}
	
	public void setRow() {
		System.out.print("Row: (0-" + (board.getSize()-1) +")");
		
		while(!sc.hasNextInt()) {
			System.out.println("Please enter an integer");
			sc.next();
		}
		
		row = sc.nextInt();
	}
	
	public void setCol() {
		System.out.print("Col: (0-" + (board.getSize()-1) +")");
		
		while(!sc.hasNextInt()) {
			System.out.println("Please enter an integer");
			sc.next();
		}
		    
		col = sc.nextInt();
	}
	

	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setDone(Player player) {
		winner = player;
		isDone = true;
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void printBoard() {
		board.printBoard();
	}
	
	public Player getWinner() {
		return winner;
	}
}
