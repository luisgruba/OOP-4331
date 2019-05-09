package Main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import View.SuperTTTGameView;
import View.TTTGameView;
import View.TTT_3D_GameView;

import java.util.Set;

import View.SuperTTTGameView;
import View.TTTGameView;
import Objects.Player;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class Main extends Application {
	
	private int numPlayers;
	private String player1, player1Marker, player2, player2Marker;
	private String[]playerDetails = new String[4];
	private int time;
	
	private Player[]players = new Player[2];
	private int gridX,gridY;
	private Player currentPlayer;
	private boolean playerClicked = false;
	private String gameSelected;
	private int gameMode;
	private boolean rotation;
	
	HashMap<String, String> playerMarker = new HashMap<String, String>();
	HashMap<String, Integer> playerRecord = new HashMap<String, Integer>();
	
	Stage window;
	Scene scene1,scene2,scene3,gameScene;
	Scene[] scenes = {scene1,scene2,scene3,gameScene};
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		getRecord();
        getMarkers();
       
		window = primaryStage;
		window.setTitle("TTT GUI");
		
		// SCENE 1
	    GridPane grid = new GridPane();
	    grid.setPadding(new Insets(10, 10, 10, 10));
	    grid.setVgap(8);
	    grid.setHgap(10);

	    //Number of Players
	    Label label = new Label("How Many Players");
	    final Spinner<Integer> spinner = new Spinner<Integer>();
	    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2, 1);
	    spinner.setValueFactory(valueFactory);
	    
	    //Timer settings
	    Label timerLabel = new Label("Timer length (in seconds)");
	    final Spinner<Integer> timerSpinner = new Spinner<Integer>();
	    SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
	    timerSpinner.setValueFactory(valueFactory1);
	    
	    //GridPane setup
	    GridPane.setConstraints(label,0,0);
	    GridPane.setConstraints(spinner,0,1);
	    GridPane.setConstraints(timerLabel,0,2);
	    GridPane.setConstraints(timerSpinner,0,3);
	    
	    ComboBox<String> showGames = new ComboBox<>();                 
    	showGames.getItems().addAll("3x3 TicTacToe","4x4 TicTacToe","5x5 TicTacToe","Super TicTacToe","3x3x3 TicTacToe");
    	Label gameLabel = new Label("What Type of Game?");
    	showGames.setValue("3x3 TicTacToe");
    	GridPane.setConstraints(gameLabel, 0,4);
    	GridPane.setConstraints(showGames, 0,5);
	    
    	CheckBox checkBox = new CheckBox("Enable Rotation? (2D Only)");
    	checkBox.setSelected(false);
    	GridPane.setConstraints(checkBox, 0,6);
	    //Enter Button
	    Button startButton = new Button("Start");
	    GridPane.setConstraints(startButton, 0,8);
	    startButton.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
	    	public void handle(ActionEvent e) {
	    		rotation = checkBox.isSelected();
	    		numPlayers = spinner.getValue();
	    		scene2 = playerSetUp(numPlayers);
	    		time = timerSpinner.getValue();
	    		getRecord();
	            getMarkers();
	    		window.setScene(scene2);
	    		
	    		gameSelected = showGames.getValue();
	    		
	    		switch(gameSelected) {
	    		case "3x3 TicTacToe" :
	    			gameMode = 1;
	    			break;
	    		case "4x4 TicTacToe":
	    			gameMode = 2;
	    			break;
	    		case "5x5 TicTacToe":
	    			gameMode = 3;
	    			break;
	    		case "Super TicTacToe":
	    			gameMode = 4;
	    			break;
	    		case "3x3x3 TicTacToe":
	    			gameMode = 5;
	    			break;
	    		}
	    		System.out.println(gameMode);
	    		
	    	}
	    });
	    
	    grid.getChildren().addAll(label, spinner, startButton, timerLabel, timerSpinner, showGames, gameLabel, checkBox);
	    scene1 = new Scene(grid,300,300);
	    window.setScene(scene1);
	    window.show();
	}
	public Scene playerSetUp(int numPlayers) {
		
		// SCENE 2
	    GridPane grid2 = new GridPane();
	    grid2.setPadding(new Insets(10, 10, 10, 10));
	    grid2.setVgap(8);
	    grid2.setHgap(10);
	    
	    //Player Set-up
	    if(numPlayers == 1) {
	    	Label player1Name = new Label("Enter Player 1 Name");
	    	TextField player1NameInput = new TextField("Player 1");
	    	Button setPlayer = new Button("Enter");
	    	
	    	ComboBox<String> nameBox = new ComboBox<>();	
	    	for(Entry<String, String> e : playerMarker.entrySet()){                  
	    		nameBox.getItems().add(e.getKey());
	    	}
	    	ComboBox<String> showRecords = new ComboBox<>();
	    	for(Entry<String, Integer> e : playerRecord.entrySet()){                  
	    		showRecords.getItems().add(e.getKey() + ": " + e.getValue() +" wins");
	    	}
	    	showRecords.setValue("Click to see records");
	    	nameBox.setEditable(true);
	    	nameBox.setValue("Player 1");
	    	
	    	
	        ComboBox<String> comboBox = new ComboBox<>();
	        comboBox.getItems().addAll(
	                "X",
	                "O"
	        );

	        comboBox.setPromptText("Select marker or type");
	        comboBox.setEditable(true);
	        comboBox.setValue("X");
	        
	    	GridPane.setConstraints(player1Name, 0, 0);
	    	GridPane.setConstraints(nameBox, 0, 1);
	    	GridPane.setConstraints(comboBox, 0,2);
	    	GridPane.setConstraints(showRecords, 0,3);
	    	GridPane.setConstraints(setPlayer,0,4);
	    	grid2.getChildren().addAll(player1Name, nameBox, setPlayer, comboBox, showRecords);
	    	
	    	setPlayer.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override
		    	public void handle(ActionEvent e) {
		    		if(nameBox.getValue() == null || nameBox.getValue().length() == 0 ) {
		    			player1 = "Player 1";
		    		}
		    		else
		    			player1 = nameBox.getValue();
		    		
		    		if(comboBox.getValue().length() == 0) {
		    			comboBox.setValue(playerMarker.get(player1));
		    			player1Marker = comboBox.getValue();
		    		}
		    		else {
		    			
		    			player1Marker = comboBox.getValue();
		    		}
		    		
		    		//Store player data (marker)
		    		if(playerMarker.containsKey(player1)) {
		    			if(!playerMarker.get(player1).equals(player1Marker))
		    				playerMarker.replace(player1, player1Marker);
		    		}
		    		else {
		    			playerMarker.put(player1, player1Marker);
		    		}
		    		
		    		if(!playerRecord.containsKey(player1)) {
		    			playerRecord.put(player1, 0);
		    		}
		    		populateMarkers();
		    		populateRecord();
		    		
		    		players[0] = new Player(player1, player1Marker);
		    		players[0].setPosition(1);
		    		players[1] = new Player("Computer", "CPU");
		    		players[1].setPosition(2);
		    		gameSetUp();
		    	}
		    });
	    }
	    else {
	    	Label player1Name = new Label("Enter Player 1 Name");
	    	//TextField player1NameInput = new TextField("Player 1");
	    	Label player2Name = new Label("Enter Player 2 Name");
	    	//TextField nameBox1 = new TextField("Player 2");
	    	Button setName = new Button("Enter");
	    	
	    	ComboBox<String> nameBox = new ComboBox<>();
	    	for(Entry<String, String> e : playerMarker.entrySet()){                  
	    		nameBox.getItems().add(e.getKey());
	    	}
	    	nameBox.setEditable(true);
	    	nameBox.setValue("Player 1");
	    	
	    	ComboBox<String> showRecords = new ComboBox<>();
	    	for(Entry<String, Integer> e : playerRecord.entrySet()){                  
	    		showRecords.getItems().add(e.getKey() + ": " + e.getValue() +" wins");
	    	}
	    	showRecords.setValue("Click to see records");
	    	
	    	ComboBox<String> nameBox1 = new ComboBox<>();
	    	
	    	for(Entry<String, String> e : playerMarker.entrySet()){                  
	    			nameBox1.getItems().add(e.getKey());
	    	}
	    	nameBox1.setEditable(true);
	    	nameBox1.setValue("Player 2");
	    	
	    	ComboBox<String> comboBox = new ComboBox<>();
	        comboBox.getItems().addAll(
	                "X",
	                "O"
	        );
	        
	        ComboBox<String> comboBox1 = new ComboBox<>();
	        comboBox1.getItems().addAll(
	                "X",
	                "O"
	        );

	        comboBox.setPromptText("Select marker or type");
	        comboBox.setEditable(true);
	        comboBox.setValue("X");
	        comboBox1.setPromptText("Select marker or type");
	        comboBox1.setEditable(true);
	        comboBox1.setValue("O");
	    	
	    	GridPane.setConstraints(player1Name, 0, 0);
	    	GridPane.setConstraints(nameBox, 0, 1);
	    	GridPane.setConstraints(comboBox, 1,1);
	    	GridPane.setConstraints(player2Name, 0, 2);
	    	GridPane.setConstraints(nameBox1, 0, 3);
	    	GridPane.setConstraints(comboBox1, 1,3);
	    	GridPane.setConstraints(showRecords, 0,4);
	    	GridPane.setConstraints(setName,0,5);
	    	grid2.getChildren().addAll(player1Name, nameBox, player2Name, nameBox1,setName, comboBox, comboBox1, showRecords);
	    	setName.setOnAction(new EventHandler<ActionEvent>() {
		    	@Override
		    	public void handle(ActionEvent e) {
		    		if(nameBox.getValue() == null || nameBox.getValue().length() == 0 ) {
		    			player1 = "Player 1";
		    		}
		    		else
		    			player1 = nameBox.getValue();
		    		
		    		if(comboBox.getValue().length() == 0) {
		    			player1Marker = "X";
		    		}
		    		else
		    			player1Marker = comboBox.getValue();
		    		
		    		if(nameBox1.getValue() == null || nameBox1.getValue().length() == 0 ) {
		    			player2 = "Player 2";
		    		}
		    		else
		    			player2 = nameBox1.getValue();
		    		
		    		if(comboBox1.getValue().length() == 0) {
		    			player2Marker = "O";
		    		}
		    		else
		    			player2Marker = comboBox1.getValue();
		    		
		    		if(player1.equals(player2)) {
		    			player2+="_1";
		    		}
		    		if(player1Marker.equals(player2Marker)) {
		    			player2Marker+="_1";
		    		}
		    		
		    		//Store player data (marker)
		    		if(playerMarker.containsKey(player1)) {
		    			if(!playerMarker.get(player1).equals(player1Marker))
		    				playerMarker.replace(player1, player1Marker);
		    		}
		    		else {
		    			playerMarker.put(player1, player1Marker);
		    		}
		    		if(playerMarker.containsKey(player2)) {
		    			if(!playerMarker.get(player2).equals(player2Marker))
		    				playerMarker.replace(player2, player2Marker);
		    		}
		    		else {
		    			playerMarker.put(player2, player2Marker);
		    		}
		    		
		    		//Store player data (records)
		    		if(!playerRecord.containsKey(player1)) {
		    			playerRecord.put(player1, 0);
		    		}
		    		if(!playerRecord.containsKey(player2)) {
		    			playerRecord.put(player2, 0);
		    		}
		    		populateMarkers();
		    		populateRecord();
		    		players[0] = new Player(player1, player1Marker);
		    		players[0].setPosition(1);
		    		players[1] = new Player(player2, player2Marker);
		    		players[1].setPosition(2);
		    		gameSetUp();
		    		
		    		}
		    	});
	    	}
	    scene2 = new Scene(grid2, 400,400);
	    return scene2;
	}
	
	public void gameSetUp() {
		switch(gameMode) {
		
		case 1:
			TTTGameView aView = new TTTGameView(3,players, time, rotation);
			gameScene = aView.getMainScene();
			break;
		case 2:
			TTTGameView bView = new TTTGameView(4,players, time, rotation);
			gameScene = bView.getMainScene();
			break;
		case 3:
			TTTGameView cView = new TTTGameView(5,players, time, rotation);
			gameScene = cView.getMainScene();
			break;
		case 4:
			SuperTTTGameView dView = new SuperTTTGameView(players, rotation);
			gameScene = dView.getMainScene();
			break;
		case 5:
			TTT_3D_GameView eView = new TTT_3D_GameView(players);
			gameScene = eView.getMainScene();
		}
		window.setScene(gameScene);
	    window.show();
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
	public void getMarkers() {
		try
	      {
	         FileInputStream fis = new FileInputStream("marker.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         playerMarker = (HashMap) ois.readObject();
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
	      Set set = playerMarker.entrySet();
	      Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         System.out.print("key: "+ mentry.getKey() + " & Value: ");
	         System.out.println(mentry.getValue());
	      }
	}
	
	public void populateMarkers() {
		try
      {
             FileOutputStream fos = new FileOutputStream("marker.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
             oos.writeObject(playerMarker);
             oos.close();
             fos.close();
             System.out.printf("Serialized HashMap data is saved in marker.ser");
      }catch(IOException ioe)
       {
             ioe.printStackTrace();
       }
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
}

