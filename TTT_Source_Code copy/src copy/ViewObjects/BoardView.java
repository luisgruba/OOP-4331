package ViewObjects;

import Objects.Board;
import Objects.Player;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class BoardView extends StackPane{
	
	private CellView[][]board;
	private Board gameBoard;
	private Cubito qbo;
	
	private int size;
	private boolean isWon;
	private boolean isBusy;
	private int row;
	private int col;
	private boolean isRotate;
	private int orderNum;
	
	public BoardView(int n) {
		size = n;
		orderNum = -1;
		qbo = new Cubito(size);
		gameBoard = new Board(size);
		isBusy = false;
		isRotate = false;
		
		board = new CellView[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				CellView square = new CellView(i,j);
				board[i][j] = square;
			}
		}
		
		
		isWon = false;
	}
	
	public BoardView(int n, int s) {
		size = n;
		orderNum = s;
		qbo = new Cubito(size);
		gameBoard = new Board(size);
		isBusy = false;
		isRotate = false;
		
		board = new CellView[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				CellView square = new CellView(i,j);
				board[i][j] = square;
			}
		}
		
		
		isWon = false;
	}
	
	public int getOrderNum() {
		return orderNum;
	}
	
	public void setRotate(boolean rotate) {
		isRotate = rotate;
	}
	
	public void reset() {
		qbo = new Cubito(size);
		gameBoard = new Board(size);
		isBusy = false;
		
		board = new CellView[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				CellView square = new CellView(i,j);
				board[i][j] = square;
			}
		}
		isWon = false;
	}
	
	public GridPane getBoard() {
		
		GridPane gridPane = new GridPane();
		GridPane grid = new GridPane();
		Cubito qbo = getCubito();
        for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				GridPane.setConstraints(board[i][j],i,j);
				gridPane.getChildren().add(board[i][j]);
			}
		}
		
		gridPane.setHgap(3);
		gridPane.setVgap(3);
		
		Group combo = new Group();
		combo.getChildren().addAll(qbo.getRectangle(),gridPane);
		grid.getChildren().add(combo);
		grid.setAlignment(Pos.CENTER);
		//grid.setGridLinesVisible(true);
		if(isRotate)
			grid.setRotate((int)(Math.random() * 360));
		
		return grid;
	}
	
	public void insert(int row, int col, boolean busy, Player player) {
		if(!busy) {
			System.out.println(player.getName() + "'s turn");
			isWon = gameBoard.insert(row, col, player);
			board[row][col].setClick(player);
			board[row][col].setOnMouseClicked(null);
			if(!isWon && gameBoard.isFull())
				System.out.println("TIE");
			else if(!isWon) {
				
			}
			else {
				setBusy(true);
				alertWin(player);
				System.out.println(player.getName() + " wins!");
				allClicked();
			}
		}	
	}

	public boolean isBusy() {
		if(hasWinner())
			return true;
		return isBusy;
	}
	
	public CellView getSelection(int i, int j) {
		return board[i][j];
	}
	
	public void alertWin(Player player) {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(board[i][j].getText().equals(player.getMarker()))
					board[i][j].setText(player.getName());
			}
		}
	}
	
	public void setBusy(boolean busy) {
		isBusy = busy;
	}
	
	public Board getGameBoard() {
		return gameBoard;
	}
	
	public boolean hasWinner() {
		return isWon;
	}
	
	public void allClicked() {
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) 
				board[i][j].setOnMouseClicked(null);
	}
	
	public Cubito getCubito() {
		return qbo;
	}	
	
	public void setColor() {
		if(!isBusy)
			qbo.pickedColor();
		qbo.openColor();
	}
	
	public void setCoordinates(int i, int j) {
		row = i;
		col = i;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	
}

