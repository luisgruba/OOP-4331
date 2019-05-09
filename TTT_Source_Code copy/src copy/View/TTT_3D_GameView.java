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

import Objects.Board;
import Objects.Player;
import ViewObjects.BoardView;
import ViewObjects.CellView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class TTT_3D_GameView {
	
	private final int windowWidth = 350;
    private final int windowHeight = 600;
	
	private BorderPane root;
	private Scene scene; 
	
	private BoardView[]board;
	private BoardView[]rowBoards;
	private BoardView[]colBoards;
	
	private Player currPlayer;
	private Player[]players;
	
	private HashMap<String, Integer> playerRecord = new HashMap<String, Integer>();
	
	public TTT_3D_GameView(Player[] players) {
		getRecord();
		this.players = players;
		board = new BoardView[3];
		rowBoards = new BoardView[3];
		colBoards = new BoardView[3];
		for(int i = 0; i < 3; i++) {
			board[i] = new BoardView(3, i);
			rowBoards[i] = new BoardView(3);
			colBoards[i] = new BoardView(3);
		}
		
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);
		this.root.setCenter(this.getDisplay());
		currPlayer = players[0];
		enableTaps();
		board[1].getSelection(1,1).setOnMouseClicked(null);
		board[1].getSelection(1, 1).setClick(new Player("",""));
		this.root.setLeft(this.getDetails());
		this.root.setTop(this.getTitle());
		this.root.setBottom(this.getBottom());
		System.out.println(board[1].getSelection(1, 1).wasClicked());
	}
	
	public Scene getMainScene() {
		return this.scene;
	}
	
	public FlowPane getDisplay() {
		board[1].getSelection(1, 1).turnOff();
		FlowPane flow = new FlowPane();
		flow.setAlignment(Pos.CENTER);
		flow.setRowValignment(VPos.BOTTOM);
		for(int i = 0; i < board.length; i++)
				flow.getChildren().add(board[i].getBoard());
		flow.setHgap(10);
		flow.setVgap(15);
		
		return flow;
	}
	
	public void enableTaps() {
		for(int a = 0; a < 3; a++) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					CellView selection = board[a].getSelection(i, j);
					int keep = board[a].getOrderNum();
					selection.setOnMouseClicked(e -> playTTT(selection, keep));
					System.out.println("ENABLED");
				}
			}
		}
	board[1].getSelection(1,1).setOnMouseClicked(null);
	board[1].getSelection(1, 1).setClick(new Player("",""));
	}
	
	public void playTTT(CellView selection, int index) {
		System.out.println(index +":"+selection.getCol() +":"+ selection.getRow());
		board[index].insert(selection.getRow(), selection.getCol(), board[index].isBusy(), currPlayer);
		rowBoards[selection.getCol()].insert(index, selection.getRow(), rowBoards[selection.getCol()].isBusy(), currPlayer);
		colBoards[selection.getRow()].insert(index, selection.getCol(), colBoards[selection.getRow()].isBusy(), currPlayer);
		boolean win = checkWins();
		this.root.setCenter(this.getDisplay());
		board[index].getGameBoard().printBoard();
		System.out.println();
		System.out.println();
		rowBoards[selection.getCol()].getGameBoard().printBoard();
		System.out.println();
		System.out.println();
		colBoards[selection.getRow()].getGameBoard().printBoard();
		if(!win && checkFull()) {
			this.root.setLeft(winStatus(0, currPlayer));
		}
		else if(!win) {
			changePlayers();
			if(currPlayer.getMarker().equals("CPU")) {
				computerMove();
			}
			this.root.setLeft(this.getDetails());
		}
		else {
			System.out.println("Winner is " + currPlayer.getName());
			this.root.setLeft(winStatus(1, currPlayer));
			disableTaps();
			if(!currPlayer.getMarker().contentEquals("CPU")) {
				playerRecord.replace(currPlayer.getName(), playerRecord.get(currPlayer.getName())+1);
	    		populateRecord();
			}
		}
	}
	
	public void changePlayers() {
		System.out.println("PLAYERS CHANGED");
		if(currPlayer == players[0])
			currPlayer = players[1];
		else
			currPlayer = players[0];
	}
	
	public void computerMove() {
		int i = 1;
		int index = (int)(Math.random() *3);
		int row = (int)(Math.random() * 3);
		int col = (int)(Math.random() * 3);
		boolean check = board[index].getSelection(col, row).wasClicked();
		while(check){
			System.out.println("CPU Deciding");
			if(i > 27){
				this.root.setLeft(this.winStatus(0,currPlayer));
				break;
			}
			index = (int)(Math.random() * 3);
			row = (int)(Math.random() * 3);
			col = (int)(Math.random() * 3);
			check = board[index].getSelection(col, row).wasClicked();
			i++;
		}
		playTTT(new CellView(col,row), index);
	}
	
	public VBox winStatus(int i, Player player) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		
		if(i == 1) {
		Text title = new Text(player.getName() + " WINS");
		System.out.println("The winner should be displayed: " + currPlayer.getName());
		title.setFont(Font.font("Verdana", 18));
		vbox.getChildren().addAll(title);
		}
		else{
			Text title = new Text("Status");
			Text title1 = new Text("TIE GAME");
			title.setFont(Font.font("Verdana", 15));
			vbox.getChildren().addAll(title,title1);
		}
		
		 
		return vbox;
	}
	
	public boolean checkWins() {
		boolean result = false;
		boolean top = false;
		boolean row = false;
		boolean col = false;
		System.out.println("CHECKING WIN");
		for(int i = 0; i < 3; i++) {
			top = board[i].hasWinner();
			board[i].getGameBoard().printBoard();
			System.out.println(top);
			row = rowBoards[i].hasWinner();
			board[i].getGameBoard().printBoard();
			System.out.println(row);
			col = colBoards[i].hasWinner();
			board[i].getGameBoard().printBoard();
			System.out.println(col);
			if(top||row||col) {
				result = true;
				break;
			}	
		}
		System.out.println("DONE CHECKING");
		return result;	
	}
	
	public boolean checkFull() {
		boolean result = true;
		boolean top = false;
		boolean row = false;
		boolean col = false;
		System.out.println("CHECKING WIN");
		for(int i = 0; i < 3; i++) {
			top = board[i].getGameBoard().isFull();
			row = rowBoards[i].getGameBoard().isFull();
			col = colBoards[i].getGameBoard().isFull();
			System.out.println(col);
			if(!top || !row || !col) {
				result = false;
			}	
		}
		System.out.println("DONE CHECKING");
		return result;	
	}
	
	public void disableTaps() {
		for(int a = 0; a < 3; a++) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					CellView selection = board[a].getSelection(i, j);
					selection.setOnMouseClicked(null);
					System.out.println("DISABLED");
				}
			}
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
	
	public HBox getTitle() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(8);
		
		Text title = new Text("3D TIC TAC TOE");
	    
		title.setFont(Font.font("Verdana", FontPosture.ITALIC, 22));
		
		hbox.getChildren().addAll(title);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}
	
	public void reset() {
		disableTaps();
		System.out.println("HERE");
		for(int i = 0; i < 3; i++) {
			board[i].reset();
			rowBoards[i].reset();
			colBoards[i].reset();
			System.out.println(i);
		}
		enableTaps();
		root.setCenter(this.getDisplay());
		root.setLeft(getDetails());
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
}
