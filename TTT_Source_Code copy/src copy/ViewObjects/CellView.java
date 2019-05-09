package ViewObjects;

import Objects.Player;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CellView extends StackPane {
	
        private int x, y;
        private Rectangle border = new Rectangle(45.59, 45.59);
        private Text text = new Text();
        private boolean wasClicked = false;
        
        public CellView(int x, int y) {
            this.x = x;
            this.y = y;
            border.setStroke(Color.GRAY);
            text.setFill(Color.GHOSTWHITE);

            getChildren().addAll(border, text);
            
        }
        public void setText(String s) {
        	if(!wasClicked)
        		text.setText(s);
        }
        
        public String getText() {
        	return text.getText();
        }
        
        public void setClick(Player player) {
        	setText(player.getMarker());
        	wasClicked = true;
        } 
        
        public boolean wasClicked() {
        	return wasClicked;
        }
        
        public int getRow() {
        	return x;
        }
        
        public int getCol() {
        	return y;
        }
        
        public void turnOff() {
        	border.setFill(Color.CORNFLOWERBLUE);
        	border.setStroke(Color.CORNFLOWERBLUE);
        }
        
 }

