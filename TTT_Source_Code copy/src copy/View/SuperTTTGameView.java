package View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Objects.Player;
import ViewObjects.BoardView;
import ViewObjects.CellView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class SuperTTTGameView extends StackPane{

	private final int windowWidth = 650;
    private final int windowHeight = 600;
    
	private BorderPane root;
	private Scene scene; 
	
    private BoardView[][] board;
    private Player[] players;
    private Player currPlayer;
    private int[]rows;
	private int[]cols;
	private int diag;
	private int antiDiag;
    private int size;
    private boolean gameOver;
  
	private HashMap<String, Integer> playerRecord = new HashMap<String, Integer>();
	
	
	public SuperTTTGameView(Player[] players, boolean rotation) {
		getRecord();
		size = 3;
		this.players = players;
		board = new BoardView[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				BoardView view = new BoardView(3);
				view.setRotate(rotation);
				view.setCoordinates(i, j);
				board[i][j] = view;
			}
		}
		
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);
		currPlayer = players[0];
		this.root.setCenter(this.displayBoardPane());
		this.root.setLeft(this.getDetails());
		this.root.setTop(this.getTitle());
		this.root.setBottom(this.getBottom());
		enableAllGrids();
		rows = new int[size];
		cols = new int[size];
		gameOver = false;
	}
	
	public Scene getMainScene() {
		return this.scene;
	}
	 
	public FlowPane displayBoardPane() {
			
		FlowPane flow = new FlowPane(); 
		flow.setAlignment(Pos.CENTER);
		flow.setRowValignment(VPos.BOTTOM);
	    for(int i = 0; i < size; i++) {
	    	for(int j = 0; j < size; j++) {
				flow.getChildren().add(board[i][j].getBoard());
			}
		}
			
	    flow.setHgap(10);
		flow.setVgap(15);
			
		return flow;
	}   
	
	public void enableAllGrids() {
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) {
				if(!board[i][j].hasWinner()) {
					enableGrid(i,j);
				}
			}
	}
	
	public void enableGrid(int row, int col) {
		board[row][col].getCubito().pickedColor();
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) {
				CellView selection = board[row][col].getSelection(i, j);
				selection.setOnMouseClicked(e -> playTTT(selection, row, col));
			}
	}
	
	public HBox getTitle() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(8);
		
		Text title = new Text("SUPER TIC TAC TOE");
	    
		title.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		title.setFill(Color.ORANGE);
		hbox.getChildren().addAll(title);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}
	
	public void playTTT(CellView selection, int row, int col) {
		disableAllGrids();
		int i = selection.getRow();
		int j = selection.getCol();
		board[row][col].insert(i, j, board[row][col].isBusy(), currPlayer);
		boolean won = isWin(row,col);
		if(won) 
			gameOver = isWin(row,col,currPlayer.getPosition());
		
		System.out.println(gameOver);
		
		if(!gameOver) {
			System.out.println(row + ":" + col + "= prev Board");
			System.out.println(j + ":" + i + "= next Board");
			if(!board[j][i].hasWinner()) {
				enableGrid(j,i);
				board[row][col].getCubito().openColor();
				board[j][i].getCubito().pickedColor();
				System.out.println("No Winner");
				changePlayers();
				this.root.setLeft(this.getDetails());
				if(currPlayer.getMarker().equals("CPU"))
					computerMove(1, j, i);
			}
			else {
				enableAllGrids();
				changePlayers();
				this.root.setLeft(this.getDetails());
				if(currPlayer.getMarker().equals("CPU"))
					computerMove(0, -1, -1);
			}
		}
		else {
			if(!currPlayer.getMarker().contentEquals("CPU")) {
				playerRecord.replace(currPlayer.getName(), playerRecord.get(currPlayer.getName())+1);
	    		populateRecord();
			}
			System.out.println("GAME OVER");
		}
		root.setCenter(displayBoardPane());
	}
	
	public void computerMove(int status, int i, int j) {
		
		if(status == 1) {
			int k = 1;
			int row = (int)(Math.random() * size);
			int col = (int)(Math.random() * size);
			boolean check = board[i][j].getSelection(col, row).wasClicked();
			while(check){
				System.out.println("CPU Deciding");
				if(k > 9){
					//this.root.setLeft(this.winStatus(0,currPlayer));
					break;
				}
				row = (int)(Math.random() * size);
				col = (int)(Math.random() * size);
				check = board[i][j].getSelection(col, row).wasClicked();
				k++;
			}
			playTTT(new CellView(col,row),i,j);
		}
		else {
			int k = 1;
			int row = (int)(Math.random() * size);
			int col = (int)(Math.random() * size);
			boolean check = board[row][col].hasWinner();
			while(check){
				System.out.println("CPU Deciding");
				if(k > 9){
					//this.root.setLeft(this.winStatus(0,currPlayer));
					break;
				}
				row = (int)(Math.random() * size);
				col = (int)(Math.random() * size);
				check = board[row][col].hasWinner();
				k++;
			}
			computerMove(1,row,col);
		}	
	}
	
	public void disableAllGrids() {
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) {
				board[i][j].getCubito().openColor();
				disableGrid(i,j);
			}
	}
	
	public void disableGrid(int row, int col) {
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) {
				CellView selection = board[row][col].getSelection(i, j);
				selection.setOnMouseClicked(null);
			}
	}
	
	public boolean isWin(int i, int j) {
		return board[i][j].hasWinner();
	}
	
	public boolean isFull(int i, int j) {
		return board[i][j].getGameBoard().isFull();
	}
	
	public void changePlayers() {
		System.out.println("PLAYERS CHANGED");
		if(currPlayer == players[0])
			currPlayer = players[1];
		else
			currPlayer = players[0];
	}
	
	public VBox getControls() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		
		Text title = new Text("TEST TITLE");
	    
		Button b1 = new Button("RESTART");
		Button b2 = new Button("QUIT");
		
		Text player1 = new Text(players[0].getName()+ "'s turn");
		Text player2 = new Text(players[1].getName()+ "'s turn");
		b1.setOnMouseClicked(e -> reset());
		b2.setOnMouseClicked(e -> System.exit(0));
		
		vbox.getChildren().addAll(title,b1,b2, player1, player2);
		 
		return vbox;
	}
	
	public void reset() {
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++) 
				board[i][j].reset();
		enableAllGrids();		
		root.setCenter(displayBoardPane());
		root.setLeft(getDetails());
	}
	
	public Player getCurrentPlayer() {
		return currPlayer;
	}
	
	public boolean isWin(int row, int col, int player) {
		if (player == 1){
			rows[row]++;
			cols[col]++;

			if (row == col) 
				diag++;
			if (row + col == rows.length - 1) 
				antiDiag++;
	    }
	    else{
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
	
	public void populateRecord() {
		try
      {
             FileOutputStream fos = new FileOutputStream("record.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
             oos.writeObject(playerRecord);
             oos.close();
             fos.close();
             System.out.printf("Serialized HashMap data is saved in record.ser");
      }catch(IOException ioe)
       {
             ioe.printStackTrace();
       }
	}
	
	public void getRecord() {
	      try
	      {
	         FileInputStream fis = new FileInputStream("record.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         playerRecord = (HashMap) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(IOException ioe)
	      {
	         ioe.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return;
	      }
	      System.out.println("Deserialized HashMap..");
	      // Display content using Iterator
	      Set set = playerRecord.entrySet();
	      Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("key: "+ mentry.getKey() + " & Value: ");
	         System.out.println(mentry.getValue());
	      }
	}
	
	public VBox getDetails() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(15));
		vbox.setSpacing(15);
		
		Text title = new Text("Status");
	    
		
		
		Text player1 = new Text(currPlayer.getName() + "'s turn");
		player1.setFont(Font.font("Verdana", 14));
		
		vbox.getChildren().addAll(title,player1);
		 
		return vbox;
	}
	
	public HBox getBottom() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(8);
		
		Button b1 = new Button("RESTART");
		Button b2 = new Button("QUIT");
		
		b1.setOnMouseClicked(e -> reset());
		b2.setOnMouseClicked(e -> System.exit(0));
		
		b1.setAlignment(Pos.BOTTOM_LEFT);
		b2.setAlignment(Pos.BOTTOM_LEFT);
		hbox.getChildren().addAll(b1,b2);
		
		return hbox;
	}
	
}