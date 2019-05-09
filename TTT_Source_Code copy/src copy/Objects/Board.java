package Objects;

public class Board {
	
	private Cell[][]board;
	private int size;
	private int openCells;
	private int[]rows;
	private int[]cols;
	private int diag;
	private int antiDiag;
	
	public Board(int size) {
		
		this.size = size;
		board = new Cell[size][size];
		openCells = size*size;
		rows = new int[size];
		cols = new int[size];
		diag = 0;
		antiDiag = 0;
		
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) {
				Cell temp = new Cell(i,j);
				board[i][j] = temp;
			}
	}
	
	public boolean insert(int row, int col, Player player) {
		System.out.println(row + ":" + col + " - board insert");
		//printWinData();
		
		//if((row >= 0 && row < size) && (col >= 0 && col < size)) {
			if(!board[row][col].isOccupied()) {
				board[row][col].setOccupied(player);
				openCells--;
				return isWin(row, col, player.getPosition());
			}/*
			else {
				System.out.println("Previously Selected Space");
				return false;
			}
		}
		else
			System.out.println("Out of Bounds selected");*/
		return false;
	}
	
	public boolean isWin(int row, int col, int player) {
		//printWinData();
		
		if (player == 1)
	    {
			//System.out.println(rows[row] + ":" + cols[col] + " -board isWin");
			rows[row]++;
			cols[col]++;

			if (row == col) 
				diag++;
			if (row + col == rows.length - 1) 
				antiDiag++;
			
	    }
	    else
	    {
	    	//System.out.println(rows[row] + ":" + cols[col] + " -board isWin");
	    	rows[row]--;
	    	cols[col]--;

	    	if (row == col) 
	    		diag--;
	    	if (row + col == rows.length - 1) 
	    		antiDiag--;
	    }
		boolean player1Win = diag == size || rows[row] == size || cols[col] == size || antiDiag == size;
		boolean player2Win = diag ==  -1*size|| rows[row] == -1*size || cols[col] == -1*size || antiDiag == -1*size;
		
		if(player1Win || player2Win) 
			return true;
		
	    return false;
	}
	
	public void printWinData() {
		System.out.print("Rows: ");
		for(int i = 0; i < size; i++) 
			System.out.print(rows[i] + " ");
		
		System.out.println();
		
		System.out.print("Cols: ");
		for(int i = 0; i < size; i++) 	
			System.out.print(cols[i] + " ");
		
		System.out.println();
		System.out.println(diag);
		System.out.println(antiDiag);
	}
	
	public void printBoard() {
		for(Cell[] cell: board) {
			for(int i = 0; i <cell.length; i++) {
				cell[i].printCell();
			}
			System.out.println();
			for(int i = 0; i < size; i++)
				System.out.print("-----------------");
			System.out.println();
		}
	}
	
	public boolean isFull() {
		if(openCells == 0)
			return true;
		return false;
	}
	
	public void setNull() {
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) 
				board[i][j].setOccupied();
	}
	
	public int getSize() {
		return size;
	}

}
