package View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import Objects.Player;
import ViewObjects.BoardView;
import ViewObjects.CellView;
import ViewObjects.Clock;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import java.time.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TTTGameView extends StackPane{

	private final int windowWidth = 420;
    private final int windowHeight = 350;
    
	private BorderPane root;
	private Scene scene; 
	
    private BoardView board;
    private Player[] players;
    private Player currPlayer;
    private Clock timer;
    
    private int size;
    private HashMap<String, Integer> playerRecord = new HashMap<String, Integer>();
	
	public TTTGameView(int n, Player[] players, int time, boolean rotation) {
		getRecord();
		size = n;
		this.players = players;
		currPlayer = players[0];
		board = new BoardView(n);
		board.setRotate(rotation);
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);
		this.root.setCenter(this.displayBoardPane());
		this.root.setLeft(this.getDetails());
		this.root.setTop(this.getTitle());
		this.root.setBottom(this.getBottom()); 
			
		timer = new Clock(time);
		Pane timerPane = new Pane();
		timerPane.setPadding(new Insets(8,8,8,8));
		timer.setAlignment(Pos.CENTER);
		timerPane.getChildren().add(timer);
		this.root.setRight(timerPane);
		
		enableMoves();
		
		
	}
	
	public void doStuff() {
		
	}
	
	public Scene getMainScene() {
		return this.scene;
	}
	 
	public GridPane displayBoardPane() {
        return board.getBoard();
	}
	
	public void enableMoves() {
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) {
				CellView selection = board.getSelection(i, j);
				selection.setOnMouseClicked(e -> playTTT(selection));
			}
	}
	
	public void disableMoves() {
		for(int i = 0; i < size; i++) 
			for(int j = 0; j < size; j++) {
				CellView selection = board.getSelection(i, j);
				selection.setOnMouseClicked(null);
			}
	}
	
	public void playTTT(CellView selection) {
		board.insert(selection.getRow(), selection.getCol(), board.isBusy(), currPlayer);
		root.setCenter(displayBoardPane());
		if(!isWin()) {
			if(!board.getGameBoard().isFull()) {
				changePlayers();
				this.root.setLeft(this.getDetails());
				System.out.println("Current Player: "+ currPlayer.getName());
				if(currPlayer.getMarker().equals("CPU")) {
					System.out.println("in here");
					computerMove();
				}
			}
			else
				this.root.setLeft(this.winStatus(0,currPlayer));
		}
		else {
			if(!currPlayer.getMarker().contentEquals("CPU")) {
				playerRecord.replace(currPlayer.getName(), playerRecord.get(currPlayer.getName())+1);
	    		populateRecord();
			}
			this.root.setLeft(this.winStatus(1,currPlayer));
		}
	}
	
	public boolean isWin() {
		return board.hasWinner();
	}
	
	public VBox winStatus(int i, Player player) {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		
		if(i == 1) {
		Text title = new Text(player.getName() + " WINS");
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
	
	public void computerMove() {
		int i = 1;
		int row = (int)(Math.random() * size);
		int col = (int)(Math.random() * size);
		boolean check = board.getSelection(col, row).wasClicked();
		while(check){
			System.out.println("CPU Deciding");
			if(i > size){
				this.root.setLeft(this.winStatus(0,currPlayer));
				break;
			}
			row = (int)(Math.random() * size);
			col = (int)(Math.random() * size);
			check = board.getSelection(col, row).wasClicked();
			i++;
		}
		playTTT(new CellView(col,row));
	}
	
	public boolean isFull() {
		return board.getGameBoard().isFull();
	}
	
	public void changePlayers() {
		System.out.println("PLAYERS CHANGED");
		if(currPlayer == players[0])
			currPlayer = players[1];
		else
			currPlayer = players[0];
		this.root.setTop(getTitle());
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
		
		Text title = new Text("TIC TAC TOE");
	    
		title.setFont(Font.font("Verdana", FontPosture.ITALIC, 22));
		
		hbox.getChildren().addAll(title);
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}
	
	public void reset() {
		board.reset();
		enableMoves();
		root.setCenter(displayBoardPane());
		root.setLeft(getDetails());
	}
	
	public Player getCurrentPlayer() {
		return currPlayer;
	}
	
	public BoardView getBoardView() {
		return board;
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
















































